package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {
    }

    /**
     * @return: The same instance of this class every time
     */
    public static Singleton getInstance() {
        // write your code here
        if (null == singleton) {
            synchronized (Singleton.class) {
                if (null == singleton) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
