/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.sample;

/**
 * Created by Hughes on 2016/3/23.
 */
public class ThreadLocalSample {
    public final static ThreadLocal<String> TEST_THREAD_NAME_LOCAL = new ThreadLocal<String>();
    public final static ThreadLocal<String> TEST_THREAD_VALUE_LOCAL = new ThreadLocal<String>();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 100; i++) {
            final String name = "线程-【" + i + "】";
            final String value = String.valueOf(i);
            new Thread() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        TEST_THREAD_NAME_LOCAL.set(name);
                        TEST_THREAD_VALUE_LOCAL.set(value);
                        callA();
                    } finally {
                        TEST_THREAD_NAME_LOCAL.remove();
                        TEST_THREAD_VALUE_LOCAL.remove();
                    }
                }
            }.start();
        }
    }

    public static void callA() { callB(); }

    public static void callB() {
        System.out.println(Thread.currentThread().getName());
        new ThreadLocalSample().callC();
    }

    public void callC() { callD(); }

    public void callD() {
        System.out.println(Thread.currentThread().getName() + " -- " + TEST_THREAD_NAME_LOCAL.get() + "\t=\t" +
                TEST_THREAD_VALUE_LOCAL.get());
    }
}
