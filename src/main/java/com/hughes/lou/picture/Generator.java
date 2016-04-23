/*
 * Copyright (c) 2016. Hughes. All rights reserved.
 */

package com.hughes.lou.picture;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by Hughes on 2016/1/25.
 */
public class Generator {

    public static int DIM = 1024;

    public static void main(String[] args) {
        File file = new File("MathPic.ppm");
        System.out.println("中文");

        MethodIn140 method = new MethodIn140Impl4();
        try (DataOutputStream dataOutputStream = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(file)))) {
            if (!file.exists()) {
                file.createNewFile();
            }

            dataOutputStream.write(String.format("P6\n%d %d\n255\n", DIM, DIM).getBytes());

            for (int i = 0; i < DIM; ++i) {
                for (int j = 0; j < DIM; ++j) {
                    pixedWrite(i, j, method, dataOutputStream);
                }
                if (i != DIM - 1) {
                    dataOutputStream.write("\n".getBytes());
                }
            }

            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void pixedWrite(int i, int j, MethodIn140 method, DataOutputStream dataOutputStream) throws
            IOException {
        dataOutputStream.writeChar(method.RED(i, j));
        dataOutputStream.writeChar(' ');
        dataOutputStream.writeChar(method.GREEN(i, j));
        dataOutputStream.writeChar(' ');
        dataOutputStream.writeChar(method.BLUE(i, j));
        if (j != DIM - 1) {
            dataOutputStream.writeChar(' ');
        }
    }
}
