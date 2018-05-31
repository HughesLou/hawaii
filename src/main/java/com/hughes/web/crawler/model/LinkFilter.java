/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.web.crawler.model;

/**
 * Created by 1466811 on 12/30/2015.
 */
public class LinkFilter {

    private String url;

    public LinkFilter(String url) {
        this.url = url;
    }

    public boolean accept(String url) {
        if (url.startsWith(this.url)) {
            return true;
        } else {
            return false;
        }
    }
}
