/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes;

import com.hughes.algorithm.Sort;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description:
 * Author: 1466811
 * Date:   3:36 PM 8/2/15
 */
public class Main {

    private void test() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String string1 = String.class.newInstance();
        String string2 = (String) Class.forName(String.class.getName()).newInstance();
        AnotherOuterClass.class.newInstance();
//        Constructor.newInstance("");
    }

    public static void main(String[] args) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.offer("test1");
        concurrentLinkedQueue.add("test2");
        concurrentLinkedQueue.offer("test3");
        concurrentLinkedQueue.add("test4");

        String s1 = (String) concurrentLinkedQueue.peek();
        String s2 = (String) concurrentLinkedQueue.poll();
        String s3 = (String) concurrentLinkedQueue.remove();

        String a = "AAA";
        String b = "BBB";
        String c = "AAABBB";
        String d = a + b;
        String e = "AAA" + b;
        String f = a + "BBB";
        String g = "AAA" + "BBB";
        System.out.println("Done");

        Sort sort = new Sort();
        int[] array1 = {1, 3, 5, 7, 9};
        int[] array2 = {0, 2, 4, 6, 8};
        int[] result = sort.getSortedArray(array1, array2);
        System.out.println(result);
    }
}
