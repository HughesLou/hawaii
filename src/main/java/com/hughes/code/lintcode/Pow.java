package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class Pow {
    /**
     * @param x the base number
     * @param n the power number
     * @return the result
     */
    public double myPow(double x, int n) {
        if (0 == x) {
            return 0;
        }
        if (0 == n) {
            return 1;
        }
        double result = 1f;
        int times = Math.abs(n);
        for (int i = 0; i < times; ++i) {
            result *= x;
        }
        return n > 0 ? result : 1 / result;
    }
}
