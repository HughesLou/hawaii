package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class Fibonacci {

    /**
     * @param n: an integer
     * @return an integer f(n)
     */
    public int fibonacci(int n) {
        // write your code here
        if (n <= 0) {
            return -1;
        }
        if (n <= 2) {
            return n - 1;
        }
        //return fibonacci(n - 2) + fibonacci(n - 1);
        int n1 = 0;
        int n2 = 1;
        int sn = 0;
        for (int i = 0; i < n - 2; i++) {
            sn = n1 + n2;
            n1 = n2;
            n2 = sn;
        }
        return sn;
    }
}
