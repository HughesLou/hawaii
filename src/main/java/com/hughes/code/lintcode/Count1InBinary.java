package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class Count1InBinary {
    /**
     * @param num: an integer
     * @return: an integer, the number of ones in num
     */
    public int countOnes(int num) {
        int count = 0;
        while (0 != num) {
            num &= num -1;
            count ++;
        }
        /*int times = 0;
        while (0 != num && times < 32) {
            count += num & 1;
            num >>= 1;
        }*/
        /*for(int i = 0 ; Math.abs(num) >= Math.pow(2, i) && i < 32; i++) {
            if((num & (1<<i)) != 0)
                count++;
        }*/
        return count;
    }
}
