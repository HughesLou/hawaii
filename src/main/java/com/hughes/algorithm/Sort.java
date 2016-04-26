/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.algorithm;

/**
 * Created by 1466811 on 11/10/2015.
 */
public class Sort {

    public int[] getSortedArray(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];

        int index = 0;
        int j = 0;
        for (int i = 0; i < a.length; ++i) {
            if (j < b.length) {
                if (a[i] < b[j]) {
                    result[index++] = a[i];
                } else {
                    result[index++] = b[j++];
                    i--;
                }
            } else {
                result[index++] = a[i];
            }
        }

        return result;
    }
}
