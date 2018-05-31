/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.jit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by 1466811 on 11/12/2015.
 */
public class ByteWatcher {

    public static final int SAMPLING_INTERVAL = Integer.getInteger("samplingIntervalMillis", 500);
    public static final Consumer<Thread> EMPTY = a -> {};
    public static final BiConsumer<Thread, Long> BI_EMPTY = (a, b) -> {};
    private final Map<Thread, ByteWatcherSingleThread> ams;
    private final ScheduledExecutorService monitorService = Executors
            .newSingleThreadScheduledExecutor();
    private volatile Consumer<Thread> threadCreated = EMPTY;
    private volatile Consumer<Thread> threadDied = EMPTY;
    private volatile ByteWatch byteWatch = new ByteWatch(BI_EMPTY, Long.MAX_VALUE);

    public ByteWatcher() {
        // do this first so that the worker thread is not considered
        // a "newly created" thread
        monitorService.scheduleAtFixedRate(this::checkThreads, SAMPLING_INTERVAL, SAMPLING_INTERVAL,
                TimeUnit.MILLISECONDS);

        ams = Thread.getAllStackTraces().keySet().stream().map(ByteWatcherSingleThread::new)
                .collect(Collectors.toConcurrentMap(ByteWatcherSingleThread::getThread,
                        (ByteWatcherSingleThread am) -> am));
        // Heinz: Streams make sense, right? ;-)
    }

    public void onThreadCreated(Consumer<Thread> action) {
        threadCreated = action;
    }

    public void onThreadDied(Consumer<Thread> action) {
        threadDied = action;
    }

    public void onByteWatch(BiConsumer<Thread, Long> action, long threshold) {
        this.byteWatch = new ByteWatch(action, threshold);
    }

    public void shutdown() {
        monitorService.shutdown();
    }

    public void forEach(Consumer<ByteWatcherSingleThread> c) {
        ams.values().forEach(c);
    }

    public void printAllAllocations() {
        forEach(System.out::println);
    }

    public void reset() {
        forEach(ByteWatcherSingleThread::reset);
    }

    private void checkThreads() {
        Set<Thread> oldThreads = ams.keySet();
        Set<Thread> newThreads = Thread.getAllStackTraces().keySet();

        Set<Thread> diedThreads = new HashSet<>(oldThreads);
        diedThreads.removeAll(newThreads);

        Set<Thread> createdThreads = new HashSet<>(newThreads);
        createdThreads.removeAll(oldThreads);

        diedThreads.forEach(this::threadDied);
        createdThreads.forEach(this::threadCreated);
        ams.values().forEach(this::bytesWatch);
    }

    private void threadCreated(Thread t) {
        ams.put(t, new ByteWatcherSingleThread(t));
        threadCreated.accept(t);
    }

    private void threadDied(Thread t) {
        threadDied.accept(t);
    }

    private void bytesWatch(ByteWatcherSingleThread am) {
        ByteWatch bw = byteWatch;
        long bytesAllocated = am.calculateAllocations();
        if (bw.test(bytesAllocated)) {
            bw.accept(am.getThread(), bytesAllocated);
        }
    }

    private static class ByteWatch implements BiConsumer<Thread, Long>, Predicate<Long> {

        private final long threshold;
        private final BiConsumer<Thread, Long> byteWatch;

        public ByteWatch(BiConsumer<Thread, Long> byteWatch, long threshold) {
            this.byteWatch = byteWatch;
            this.threshold = threshold;
        }

        public void accept(Thread thread, Long currentBytes) {
            byteWatch.accept(thread, currentBytes);
        }

        public boolean test(Long currentBytes) {
            return threshold < currentBytes;
        }
    }
}
