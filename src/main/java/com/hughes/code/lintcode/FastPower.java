package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/15.
 */
public class FastPower {

    /*
     * @param a, b, n: 32bit integers
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        if (a == 0) {
            return 0;
        }
        if (n == 0) {
            return 1 % b;
        }
        long result = fastPower(a, b, n / 2);
        result *= result;
        result %= b;
        if (n % 2 == 1) {
            result *= a % b;
        }
        return (int) (result % b);
    }
}