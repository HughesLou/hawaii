package com.hughes;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.*;

/**
 * Description:
 * Author: 1466811
 * Date:   4:28 PM 8/29/15
 */
public class QRSample {

    public static void main(String[] args) {
        String string = "hello";
        ByteArrayOutputStream out = QRCode.from(string).withCharset("UTF-8").withSize(250, 250).to(ImageType.JPG).stream();


        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("QR.jpg"));
            fileOutputStream.write(out.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = QRCode.from("Test").file();
        ByteArrayOutputStream byteArrayOutputStream = QRCode.from("TEST").stream();
    }
}
