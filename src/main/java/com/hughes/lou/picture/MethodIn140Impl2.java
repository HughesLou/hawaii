/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.picture;

/**
 * Created by Hughes on 2016/1/25.
 */
public class MethodIn140Impl2 implements MethodIn140 {
    @Override
    public char RED(int i, int j) {
        return (char) (Math.pow(Math.cos(Math.atan2(j-512, i-512) / 2), 2) * 255);
    }

    @Override
    public char GREEN(int i, int j) {
        return (char) (Math.pow(Math.cos(Math.atan2(j-512, i-512)/2 - 2*Math.acos(-1)/3),2)*255);
    }

    @Override
    public char BLUE(int i, int j) {
        return (char) (Math.pow(Math.cos(Math.atan2(j-512, i-512)/2 + 2 * Math.acos(-1)/3),2)*255);
    }
}