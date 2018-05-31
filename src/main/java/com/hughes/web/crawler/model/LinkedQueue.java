/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.web.crawler.model;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 1466811 on 12/30/2015.
 */
public class LinkedQueue {

    private static Set visitedUrl = new HashSet();
    private static Queue unVisitedUrl = new PriorityQueue();

    public static Set getVisitedUrl() {
        return visitedUrl;
    }

    public static void setVisitedUrl(Set visitedUrl) {
        LinkedQueue.visitedUrl = visitedUrl;
    }

    public static void setUnVisitedUrl(Queue unVisitedUrl) {
        LinkedQueue.unVisitedUrl = unVisitedUrl;
    }

    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }

    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }

    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }

    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.poll();
    }

    public static void addUnvisitedUrl(String url) {
        if (StringUtils.isNotEmpty(url) && !visitedUrl.contains(url)
                && !unVisitedUrl.contains(url)) {
            unVisitedUrl.add(url);
        }
    }

    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }

    public static boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.isEmpty();
    }
}
