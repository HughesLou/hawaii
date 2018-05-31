/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes.web.crawler.service;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 1466811 on 12/30/2015.
 */
public class FileDownload {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownload.class);

    public String getFileNameByUrl(String url, String contentType) {
        // remove https://
        if (url.startsWith("https")) {
            url = url.substring(8);
        } else {
            url = url.substring(7);
        }
        // text/html
        if (contentType.indexOf("html") != -1) {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            return url;
        } else {
            // application/pdf
            return url.replaceAll("[\\?/:*|<>\"]", "_") + "."
                    + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    private void saveToLocal(byte[] data, String folder, String fileName) {
        try {
            DataOutputStream out = new DataOutputStream(
                    new FileOutputStream(new File(folder, fileName)));
            for (int i = 0; i < data.length; i++) {
                out.write(data[i]);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String downloadFile(String url, String folder) {
        String fileName = null;
        String proxyHostName = "10.24.129.241";
        int proxyPort = 8080;
        ProxyHost proxyHost = new ProxyHost(proxyHostName, proxyPort);
        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setProxyHost(proxyHost);
        httpClient.getParams().setAuthenticationPreemptive(true);
        httpClient.getState().setProxyCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("1466811", "lou@201512"));
        //        httpClient.getState().setProxyCredentials(new AuthScope("10.24.129.241", 8080),
        //                new UsernamePasswordCredentials("1466811", "lou@201512"));
        //        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        GetMethod getMethod = new GetMethod(url);
        //        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error("Method failed: {}", getMethod.getStatusLine());
                fileName = null;
            }
            byte[] responseBody = getMethod.getResponseBody();
            fileName = getFileNameByUrl(url,
                    getMethod.getResponseHeader("Content-Type").getValue());
            saveToLocal(responseBody, folder, fileName);
        } catch (HttpException e) {
            LOGGER.error("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return fileName;
    }
}
