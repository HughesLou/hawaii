package com.hughes.file;

import java.awt.*;
import java.io.File;

/**
 * Created by Hughes on 2016/8/10.
 */
public class AppForPDF {
    public static void main(String[] args) {

        try {
            if ((new File("sample.pdf")).exists()) {
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler sample.pdf");
                p.waitFor();
            } else {
                System.out.println("File is not exists");
            }
            System.out.println("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

class AnyPlatformAppPDF {

    public static void main(String[] args) {
        try {
            File pdfFile = new File("sample.pdf");
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }
            } else {
                System.out.println("File is not exists!");
            }
            System.out.println("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}