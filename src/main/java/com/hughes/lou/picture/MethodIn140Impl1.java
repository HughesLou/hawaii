/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.picture;

/**
 * Created by Hughes on 2016/1/25.
 */
public class MethodIn140Impl1 implements MethodIn140 {
    @Override
    public char RED(int i, int j) {
        double x = 0, y = 0;
        int k;
        for (k = 0; k++ < 256; ) {
            double a = x * x - y * y + (i - 768.0) / 512;
            y = 2 * x * y + (j - 512.0) / 512;
            x = a;
            if (x * x + y * y > 4)
                break;
        }
        return (char) (Math.log(k) * 47);
    }

    @Override
    public char GREEN(int i, int j) {
        double x = 0, y = 0;
        int k;
        for (k = 0; k++ < 256; ) {
            double a = x * x - y * y + (i - 768.0) / 512;
            y = 2 * x * y + (j - 512.0) / 512;
            x = a;
            if (x * x + y * y > 4) break;
        }
        return (char) (Math.log(k) * 47);
    }

    @Override
    public char BLUE(int i, int j) {
        double x = 0, y = 0;
        int k;
        for (k = 0; k++ < 256; ) {
            double a = x * x - y * y + (i - 768.0) / 512;
            y = 2 * x * y + (j - 512.0) / 512;
            x = a;
            if (x * x + y * y > 4) break;
        }
        return (char) (128 - Math.log(k) * 23);
    }
}
