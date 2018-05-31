/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.sample;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Hughes on 2016/3/27.
 */
public class Main {

    public static void main(String[] args) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

        concurrentLinkedQueue.offer("string1");
        concurrentLinkedQueue.offer("string2");

        concurrentLinkedQueue.add("string3");
        concurrentLinkedQueue.add("string4");
        concurrentLinkedQueue.add("string4");

        concurrentLinkedQueue.peek();
        concurrentLinkedQueue.poll();

        concurrentLinkedQueue.remove("string3");

        concurrentLinkedQueue = null;

        return;

    }
}
