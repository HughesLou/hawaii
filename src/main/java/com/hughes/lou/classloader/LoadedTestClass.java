/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.classloader;

/**
 * Created by Hughes on 2016/3/30.
 */
public class LoadedTestClass {
    public LoadedTestClass() {
        System.out.println("LoadedTestClass is loaded by: " + this.getClass().getClassLoader());
    }
}
