package com.hughes.basic.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Hughes on 2016/8/9.
 */
public class ReadConsole {

    public static void main(String[] args) {
        classic();
//        scanner();
//        console();
    }

    public static void classic() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("Enter something : ");
                String input = br.readLine();
                if ("q".equals(input)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }
                System.out.println("input : " + input);
                System.out.println("-----------\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void scanner() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter something : ");
            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("Exit!");
                break;
            }
            System.out.println("input : " + input);
            System.out.println("-----------\n");
        }
        scanner.close();
    }

    public static void console() {
        while (true) {
            System.out.print("Enter something : ");
            String input = System.console().readLine();
            if ("q".equals(input)) {
                System.out.println("Exit!");
                System.exit(0);
            }
            System.out.println("input : " + input);
            System.out.println("-----------\n");
        }
    }
}