/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.picture;

/**
 * Created by Hughes on 2016/1/25.
 */
public class MethodIn140Impl4 implements MethodIn140 {

    @Override
    public char RED(int i, int j) {
        Object object = new Object();
        double a = 0, b = 0, d, n = 0;
        for (; a * a + (d = b * b) < 4
                && n++ < 8192; b = 2 * a * b + j / 5e4 + .06, a = a * a - d + i / 5e4 + .34);
        return (char) (n / 4);
    }

    @Override
    public char GREEN(int i, int j) {
        return (char) (2 * RED(i, j));
    }

    @Override
    public char BLUE(int i, int j) {
        return (char) (4 * RED(i, j));
    }
}