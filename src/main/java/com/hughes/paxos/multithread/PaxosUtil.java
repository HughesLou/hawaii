package com.hughes.paxos.multithread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class PaxosUtil {

    private static final int CHANCE_TO_CRASH = 4; // 1/4的概率会因为网络原因丢失响应
    private static final int SLEEP_TIME = 100;
    private static Random random = new Random();

    /**
     * 提案序列号生成：保证唯一且递增。参考chubby中提议生成算法
     * @param id 提议者的ID
     * @param numCycle 生成提议的轮次
     * @param proposerNumber 提议者个数
     * @return 生成的提案id
     */
    public static int generateId(int id, int numCycle, int proposerNumber) {
        return numCycle * proposerNumber + id;
    }

    // 随机休眠，模拟网络延迟
    public static void sleepRandom() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(SLEEP_TIME));
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    public static boolean isCrashed() {
        return 0 == random.nextInt(CHANCE_TO_CRASH);
    }
}