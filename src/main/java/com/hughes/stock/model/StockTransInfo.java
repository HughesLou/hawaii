package com.hughes.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author hugheslou
 * Created on 2018/7/16.
 */
public class StockTransInfo implements Comparable<StockTransInfo>, Serializable {

    private String date;
    private String time;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private Long volume;
    private BigDecimal amount;

    public StockTransInfo(String date, String time, BigDecimal open, BigDecimal high,
            BigDecimal low, BigDecimal close, long volume, BigDecimal amount) {
        this.date = date;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.amount = amount;
    }

    public StockTransInfo(String[] values) {
        this.date = values[0];
        this.time = values[1];
        this.open = new BigDecimal(values[2]);
        this.high = new BigDecimal(values[3]);
        this.low = new BigDecimal(values[4]);
        this.close = new BigDecimal(values[5]);
        this.volume = Long.valueOf(values[6]);
        this.amount = new BigDecimal(values[7]);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransTime() {
        return date + "@" + time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockTransInfo)) {
            return false;
        }
        StockTransInfo that = (StockTransInfo) o;
        return Objects.equals(getDate(), that.getDate())
                && Objects.equals(getTime(), that.getTime())
                && Objects.equals(getOpen(), that.getOpen())
                && Objects.equals(getHigh(), that.getHigh())
                && Objects.equals(getLow(), that.getLow())
                && Objects.equals(getClose(), that.getClose())
                && Objects.equals(getVolume(), that.getVolume())
                && Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getTime(), getOpen(), getHigh(), getLow(), getClose(),
                getVolume(), getAmount());
    }

    @Override
    public String toString() {
        return "StockTransInfo{" + "date='" + date + '\'' + ", time='" + time + '\'' + ", open="
                + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume="
                + volume + ", amount=" + amount + '}';
    }

    @Override
    public int compareTo(StockTransInfo stockTransInfo) {
        return this.getTransTime().compareTo(stockTransInfo.getTransTime());
    }
}
