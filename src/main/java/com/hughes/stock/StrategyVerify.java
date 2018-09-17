package com.hughes.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hughes.stock.model.StockTransInfo;

/**
 * @author hugheslou
 * Created on 2018/7/16.
 */
public class StrategyVerify {

    private static final String SEPARATOR = "\t";
    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyVerify.class);
    private static final BigDecimal PER_SHARE = new BigDecimal(100);
    private static final BigDecimal FEE = BigDecimal.valueOf(0.0003);
    private static final BigDecimal MIN_FEE = new BigDecimal(5);
    private static final NumberFormat PERCENTAGE = NumberFormat.getPercentInstance();
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {
        StrategyVerify strategyVerify = new StrategyVerify();
        BigDecimal initPrinciple = new BigDecimal(1_000_000);
        boolean firstTrans = true;
        boolean holdMidea = false;

        PERCENTAGE.setMaximumFractionDigits(2);

        // 美的、格力差价在5%以内时，美的；大于15%时，格力
        BigDecimal low = BigDecimal.valueOf(0.05);
        BigDecimal high = BigDecimal.valueOf(0.15);

        // 输入文件格式，TAB分隔
        // date time open high low close volume amount
        Map<String, List<StockTransInfo>> mideaTransMap = strategyVerify
                .readData(strategyVerify.getClass().getClassLoader().getResource("000333"));
        Map<String, List<StockTransInfo>> greeTransMap = strategyVerify
                .readData(strategyVerify.getClass().getClassLoader().getResource("000651"));

        BigDecimal principle = initPrinciple;
        BigDecimal position = new BigDecimal(0);
        BigDecimal mideaPrice = new BigDecimal(0);
        BigDecimal greePrice = new BigDecimal(0);
        BigDecimal diffPercentage = new BigDecimal(0);

        // 记录首笔交易
        BigDecimal ftPrinciple = new BigDecimal(0);
        BigDecimal ftPosition = new BigDecimal(0);
        boolean ftMidea = false;

        // 用来缓存上一个有效交易日的最后最条交易信息
        StockTransInfo mideaStockTransInfo = null;
        StockTransInfo greeStockTransInfo = null;

        for (String date : mideaTransMap.keySet()) {
            if (date.compareTo("2018/03/22") < 0) {
                continue;
            }
            List<StockTransInfo> mideaStockTransInfoList = mideaTransMap.get(date);
            List<StockTransInfo> greeStockTransInfoList = greeTransMap.get(date);
            int mideaSize = 0;
            int greeSize = 0;
            try {
                mideaSize = mideaStockTransInfoList.size();
                Collections.sort(mideaStockTransInfoList);
            } catch (Exception e) {
                LOGGER.warn("No data for midea at {}", date);
            }
            try {
                greeSize = greeStockTransInfoList.size();
                Collections.sort(greeStockTransInfoList);
            } catch (Exception e) {
                LOGGER.warn("No data for gree at {}", date);
            }

            // 处理某一天数据不全的问题
            if (mideaSize != greeSize && mideaSize != 0 && greeSize != 0) {
                LOGGER.error("K-line records for {} doesn't match", date);
                break;
            }

            for (int i = 0; i < Math.max(mideaSize, greeSize); i++) {
                if (mideaSize != 0) {
                    mideaStockTransInfo = mideaStockTransInfoList.get(i);
                }
                if (greeSize != 0) {
                    greeStockTransInfo = greeStockTransInfoList.get(i);
                }

                // verify
                if (mideaStockTransInfo != null && greeStockTransInfo != null) {
                    int diffTime = mideaStockTransInfo.getTransTime()
                            .compareTo(greeStockTransInfo.getTransTime());
                    String time = diffTime >= 0 ? mideaStockTransInfo
                            .getTransTime() : greeStockTransInfo.getTransTime();
                    mideaPrice = mideaStockTransInfo.getOpen();
                    greePrice = greeStockTransInfo.getOpen();
                    diffPercentage = mideaPrice.subtract(greePrice).divide(greePrice, 4,
                            RoundingMode.HALF_UP);
                    if (firstTrans) {
                        // 是否买美的
                        holdMidea = diffPercentage.compareTo(low) <= 0;
                        BigDecimal price = holdMidea ? mideaPrice : greePrice;
                        BigDecimal before = new BigDecimal(principle.doubleValue());
                        position = strategyVerify.buy(principle, price);
                        principle = strategyVerify.left(principle, price, position);
                        strategyVerify.log(true, holdMidea, before, position, time, price,
                                diffPercentage, principle);
                        firstTrans = false;

                        ftPrinciple = principle;
                        ftPosition = position;
                        ftMidea = holdMidea;
                        break;
                    } else {
                        if (diffPercentage.compareTo(low) <= 0) {
                            // 买美的
                            if (!holdMidea && diffTime >= 0) {
                                // 先卖格力
                                BigDecimal amount = strategyVerify.sell(position, greePrice);
                                principle = principle.add(amount).setScale(2,
                                        RoundingMode.HALF_DOWN);
                                strategyVerify.log(false, false, amount, position, time, greePrice,
                                        diffPercentage, principle);
                                BigDecimal before = principle;
                                position = strategyVerify.buy(principle, mideaPrice);
                                principle = strategyVerify.left(principle, mideaPrice, position);
                                strategyVerify.log(true, true, before, position, time, mideaPrice,
                                        diffPercentage, principle);
                                holdMidea = true;
                                break;
                            }
                        } else if (diffPercentage.compareTo(high) >= 0) {
                            // 买格力
                            if (holdMidea && diffTime <= 0) {
                                // 先卖美的
                                BigDecimal amount = strategyVerify.sell(position, mideaPrice);
                                principle = principle.add(amount).setScale(2,
                                        RoundingMode.HALF_DOWN);
                                strategyVerify.log(false, true, amount, position, time, mideaPrice,
                                        diffPercentage, principle);
                                BigDecimal before = principle;
                                position = strategyVerify.buy(principle, greePrice);
                                principle = strategyVerify.left(principle, greePrice, position);
                                strategyVerify.log(true, false, before, position, time, greePrice,
                                        diffPercentage, principle);
                                holdMidea = false;
                                break;
                            }
                        } else {
                            // 保持当前持仓
                        }
                    }
                } else {
                    LOGGER.warn("at {}, something missing {} vs {}", mideaStockTransInfo,
                            greeStockTransInfo);
                }
            }
        }

        BigDecimal noStrategyTotal = ftPrinciple
                .add(ftPosition.multiply(PER_SHARE).multiply(ftMidea ? mideaPrice : greePrice));
        LOGGER.info("NO-STRATEGY: hold {} shares {} with {}, total {}, yield {}", ftPosition,
                strategyVerify.getName(ftMidea), CURRENCY.format(ftPrinciple),
                CURRENCY.format(noStrategyTotal), PERCENTAGE.format(noStrategyTotal
                        .subtract(initPrinciple).divide(initPrinciple, 4, RoundingMode.HALF_DOWN)));

        BigDecimal total = principle
                .add(position.multiply(PER_SHARE).multiply(holdMidea ? mideaPrice : greePrice));
        LOGGER.info("WITH-STRATEGY: hold {} shares {} with {}, diff {}, total {}, yield {}",
                position, strategyVerify.getName(holdMidea), CURRENCY.format(principle),
                diffPercentage, CURRENCY.format(total), PERCENTAGE.format(total
                        .subtract(initPrinciple).divide(initPrinciple, 4, RoundingMode.HALF_DOWN)));
        return;
    }

    private Map<String, List<StockTransInfo>> readData(URL url) {
        TreeMap<String, List<StockTransInfo>> map = new TreeMap<>();
        try (Stream<String[]> lines = Files.lines(Paths.get(url.toURI()))
                .map(line -> line.split(SEPARATOR))) {
            lines.forEach(line -> {
                try {
                    String date = line[0];
                    StockTransInfo stockTransInfo = new StockTransInfo(line);
                    List<StockTransInfo> list = map.containsKey(date) ? map
                            .get(date) : new ArrayList<>();
                    if (!list.contains(stockTransInfo)) {
                        list.add(stockTransInfo);
                        map.put(date, list);
                    }
                } catch (Exception e) {
                    LOGGER.error("Exception for {}", Arrays.toString(line));
                }
            });
        } catch (Exception e) {
            LOGGER.error("read file {} error", url, e);
        }
        return map;
    }

    private BigDecimal buy(BigDecimal principle, BigDecimal price) {
        return principle.subtract(principle.multiply(FEE).max(MIN_FEE))
                .divide(price.multiply(PER_SHARE), 0, RoundingMode.DOWN);
    }

    private BigDecimal left(BigDecimal principle, BigDecimal price, BigDecimal share) {
        BigDecimal value = share.multiply(PER_SHARE).multiply(price).setScale(2,
                RoundingMode.HALF_UP);
        BigDecimal fee = value.multiply(FEE).max(MIN_FEE).setScale(2, RoundingMode.HALF_UP);
        return principle.subtract(fee).subtract(value).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal sell(BigDecimal position, BigDecimal price) {
        BigDecimal amount = price.multiply(PER_SHARE).multiply(position).setScale(2,
                RoundingMode.HALF_UP);
        BigDecimal fee = amount.multiply(FEE).max(MIN_FEE).setScale(2, RoundingMode.HALF_UP);
        return amount.subtract(fee);
    }

    private void log(boolean buy, boolean holdMidea, BigDecimal principle, BigDecimal position,
            String time, BigDecimal price, BigDecimal diff, BigDecimal left) {
        if (buy) {
            LOGGER.info("use {} to buy {} shares {} at {} with price {} percentage {}, left {}",
                    CURRENCY.format(principle), position, getName(holdMidea), time, price, diff,
                    CURRENCY.format(left));
        } else {
            LOGGER.info("sell {} shares {} at {} with price {} percentage {} get {}, total {}",
                    position, getName(holdMidea), time, price, diff, CURRENCY.format(principle),
                    CURRENCY.format(left));
        }
    }

    private String getName(boolean midea) {
        return midea ? "midea" : "gree";
    }
}
