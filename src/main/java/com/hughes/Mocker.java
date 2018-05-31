package com.hughes;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by 1466811 on 9/1/2015.
 */
public class Mocker<T extends Exception> {

    private void pleaseThrow(final Exception t) throws T {
        throw (T) t;
    }

    public static void main(String[] args) {
        try {
            new Mocker<IOException>().pleaseThrow(new SQLException());
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("END");
    }
}
