/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.classloader;

/**
 * Created by Hughes on 2016/3/30.
 */
public class Virus {

    public Virus() {
        System.out.println("Virus is loaded by: " + this.getClass().getClassLoader());
        MyClassLoader cl = (MyClassLoader) this.getClass().getClassLoader();
        System.out.println("secret is:" + cl.getPath());
    }
}
