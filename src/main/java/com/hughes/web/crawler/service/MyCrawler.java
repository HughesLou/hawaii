/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.web.crawler.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hughes.web.crawler.model.LinkFilter;
import com.hughes.web.crawler.model.LinkedQueue;

/**
 * Created by 1466811 on 12/30/2015.
 */
public class MyCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCrawler.class);

    private void initCrawlerWithSeeds(LinkedQueue LinkedQueue, String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            LinkedQueue.addUnvisitedUrl(seeds[i]);
    }

    public void crawling(String[] seeds) {
        LinkFilter filter = new LinkFilter("http://docs.openstack.org/infra/jenkins-job-builder");
        LinkedQueue LinkedQueue = new LinkedQueue();
        initCrawlerWithSeeds(LinkedQueue, seeds);
        while (!LinkedQueue.unVisitedUrlsEmpty() && LinkedQueue.getVisitedUrlNum() <= 100) {
            String visitUrl = (String) LinkedQueue.unVisitedUrlDeQueue();
            if (visitUrl == null) continue;
            FileDownload fileDownload = new FileDownload();
            fileDownload.downloadFile(visitUrl, "C:\\Loo\\Repository\\Hughes\\Data\\Crawler");
            LinkedQueue.addVisitedUrl(visitUrl);
            Set<String> links = HtmlParser.extractLinks(visitUrl, filter);
            for (String link : links) {
                LinkedQueue.addUnvisitedUrl(link);
            }
        }
    }

    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        crawler.crawling(new String[] { "http://docs.openstack.org/infra/jenkins-job-builder" });
        LOGGER.info("Done");
    }
}
