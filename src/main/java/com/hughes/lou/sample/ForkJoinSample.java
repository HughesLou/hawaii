/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.sample;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Hughes on 2016/3/27.
 */
public class ForkJoinSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        double[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        double result = new ForkJoinSample().sumOfSquares(new ForkJoinPool(), array);
        System.out.println(result);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask forkJoinTask = forkJoinPool.submit(new Fibonacci(10));
        Integer integer = (Integer) forkJoinTask.get();
        System.out.println(integer);
    }

    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci fibonacci1 = new Fibonacci(n - 1);
            fibonacci1.fork();
            Fibonacci fibonacci2 = new Fibonacci(n - 2);
            return fibonacci2.compute() + fibonacci1.join();
        }
    }

    double sumOfSquares(ForkJoinPool pool, double[] array) {
        int n = array.length;
        Applyer applyer = new Applyer(array, 0, n, null);
        pool.invoke(applyer);
        return applyer.result;
    }

    class Applyer extends RecursiveAction {

        final double[] array;
        final int lo, hi;
        double result;
        Applyer next;

        Applyer(double[] array, int lo, int hi, Applyer next) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
            this.next = next;
        }

        double atLeaf(int l, int h) {
            double sum = 0;
            for (int i = l; i < h; ++i) {
                sum += array[i] * array[i];
            }
            return sum;
        }

        @Override
        protected void compute() {
            int l = lo;
            int h = hi;
            Applyer right = null;
            while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
                int mid = (l + h) >>> 1;
                right = new Applyer(array, mid, h, right);
                right.fork();
                h = mid;
            }

            double sum = atLeaf(l, h);
            while (right != null) {
                if (right.tryUnfork()) {
                    sum += right.atLeaf(right.lo, right.hi);
                } else {
                    right.join();
                    sum += right.result;
                }
                right = right.next;
            }
            result = sum;
        }
    }

    class SortTask extends RecursiveAction {

        static final int THRESHOLD = 100;
        final long[] array;
        final int lo, hi;

        SortTask(long[] array, int lo, int hi) {
            this.array = array;
            this.lo = lo;
            this.hi = hi;
        }

        SortTask(long[] array) {
            this(array, 0, array.length);
        }

        @Override
        protected void compute() {
            if (hi - lo < THRESHOLD) {
                sortSequentially(lo, hi);
            } else {
                int mid = (hi + lo) >>> 1;
                invokeAll(new SortTask(array, lo, mid), new SortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }

        void sortSequentially(int lo, int hi) {
            Arrays.sort(array, lo, hi);
        }

        void merge(int lo, int mid, int hi) {
            long[] buf = Arrays.copyOfRange(array, lo, hi);
            for (int i = 0, j = lo, k = mid; i < buf.length; ++j) {
                array[j] = (k == hi || buf[i] < array[k]) ? buf[i++] : array[k++];
            }

        }
    }

}
