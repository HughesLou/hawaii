package com.hughes.code.lintcode;

import java.util.Arrays;

/**
 * Created by Hughes on 2016/8/14.
 */
public class SpaceReplacement {

    public static void main(String[] args) {
        replaceBlank("hello world".toCharArray(), 11);
    }

    /**
     * @param string: An array of Char
     * @param length: The true length of the string
     * @return: The true length of new string
     */
    public static int replaceBlank(char[] string, int length) {
        if (0 == length) {
            return 0;
        }
        int newLength = length;
        for (char s : string) {
            if (' ' == s) {
                newLength += 2;
            }
        }
        string = Arrays.copyOf(string, newLength);
        //        string[newLength] = 0;
        int j = 1;
        for (int i = length - 1; i >= 0; --i) {
            if (' ' == string[i]) {
                string[newLength - j++] = '0';
                string[newLength - j++] = '2';
                string[newLength - j++] = '%';
            } else {
                string[newLength - j++] = string[i];
            }
        }
        System.out.print(string);
        return newLength;
    }
}
