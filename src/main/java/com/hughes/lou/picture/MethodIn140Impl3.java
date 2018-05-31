/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.picture;

/**
 * Created by Hughes on 2016/1/25.
 */
public class MethodIn140Impl3 implements MethodIn140 {

    @Override
    public char RED(int i, int j) {
        double a = 0, b = 0, c, d, n = 0;
        while ((c = a * a) + (d = b * b) < 4 && n++ < 880) {
            b = 2 * a * b + j * 8e-9 - .645411;
            a = c - d + i * 8e-9 + .356888;
        }
        return (char) (255 * Math.pow((n - 80) / 800, 3.));
    }

    @Override
    public char GREEN(int i, int j) {
        double a = 0, b = 0, c, d, n = 0;
        while ((c = a * a) + (d = b * b) < 4 && n++ < 880) {
            b = 2 * a * b + j * 8e-9 - .645411;
            a = c - d + i * 8e-9 + .356888;
        }
        return (char) (255 * Math.pow((n - 80) / 800, .7));
    }

    @Override
    public char BLUE(int i, int j) {
        double a = 0, b = 0, c, d, n = 0;
        while ((c = a * a) + (d = b * b) < 4 && n++ < 880) {
            b = 2 * a * b + j * 8e-9 - .645411;
            a = c - d + i * 8e-9 + .356888;
        }
        return (char) (255 * Math.pow((n - 80) / 800, .5));
    }
}