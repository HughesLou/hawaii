package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class BoyerMoore {

    public static void main(String[] args) {
        int result = strStr("This is le a example", "ple");
        System.out.print(result);
    }

    public static int strStr(String source, String target) {
        // BoyerMoore
        if (null == source || null == target) {
            return -1;
        }
        if ("".equals(target)) {
            return 0;
        }
        int[] right = new int[256];
        for (int c = 0; c < 256; c++) {
            right[c] = -1;
        }
        for (int j = 0; j < target.length(); j++) {
            right[target.charAt(j)] = j;
        }
        int m = target.length();
        int n = source.length();
        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (target.charAt(j) != source.charAt(i + j)) {
                    skip = Math.max(1, j - right[source.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i; // found
            }
        }
        return -1;
    }
}
