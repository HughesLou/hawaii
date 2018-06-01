package com.hughes;

import org.apache.commons.lang3.StringUtils;

/**
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class Utils {

    public static int getIntValue(String value, int defaultValue) {
        try {
            if (StringUtils.isNotBlank(value)) {
                return Integer.valueOf(value);
            }
        } catch (Exception e) {
            System.out.println("Parameter error with value " + value);
        }
        return defaultValue;
    }
}