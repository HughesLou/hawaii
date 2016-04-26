/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes;

/**
 * Description:
 * Author: 1466811
 * Date:   4:24 PM 8/2/15
 */
public class AnotherOuterClass {

    public static void main(String[] args) {
        InnerClass inner = new AnotherOuterClass().new InnerClass();
        System.out.println("InnerClass Filed = " + inner.x);
    }

    class InnerClass {

        private int x = 10;
    }
}
