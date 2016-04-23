/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.sample;

/**
 * Created by Hughes on 2016/4/1.
 */
public class TestStatic {

    public static void main(String[] args) {
        staticFunction();
    }

    static TestStatic st = new TestStatic();

    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    TestStatic() {
        System.out.println("3");
        System.out.println("a=" + a + ",b=" + b);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}
