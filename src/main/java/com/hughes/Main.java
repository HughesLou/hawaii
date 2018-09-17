/*
 * Copyright (c) 2015. Standard Chartered Bank. All rights reserved.
 */

package com.hughes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.hughes.algorithm.Sort;

/**
 * Description:
 * Author: 1466811
 * Date:   3:36 PM 8/2/15
 */
public class Main {

    public static void main(String[] args) throws Exception {
        pcm2Wav(Files.readAllBytes(new File("/Users/hugheslou/Downloads/o.pcm").toPath()),
                "/Users/hugheslou/Downloads/o.wav");
        System.out.println("");

        Pattern pattern = Pattern.compile(", start: (.*?),");
        Matcher matcher = pattern.matcher("Duration: 00:00:00.00, start: 77.900000, bitrate: N/A");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        ClassLoader classLoader = Main.class.getClassLoader();
        for (URL url : ((URLClassLoader) classLoader).getURLs()) {
            System.out.println(url.getFile());
        }

        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.offer("test1");
        concurrentLinkedQueue.add("test2");
        concurrentLinkedQueue.offer("test3");
        concurrentLinkedQueue.add("test4");

        String s1 = (String) concurrentLinkedQueue.peek();
        String s2 = (String) concurrentLinkedQueue.poll();
        String s3 = (String) concurrentLinkedQueue.remove();

        String a = "AAA";
        String b = "BBB";
        String c = "AAABBB";
        String d = a + b;
        String e = "AAA" + b;
        String f = a + "BBB";
        String g = "AAA" + "BBB";
        System.out.println("Done");

        Sort sort = new Sort();
        int[] array1 = { 1, 3, 5, 7, 9 };
        int[] array2 = { 0, 2, 4, 6, 8 };
        int[] result = sort.getSortedArray(array1, array2);
        System.out.println(result);
    }

    /**
     * 解决保存的文件失真问题（鼻音重）
     *
     * @param data
     * @param target
     * @throws Exception
     */
    //CHECKSTYLE:OFF
    public static void pcm2Wav(byte[] data, String target) throws Exception {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat af = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        try (FileOutputStream out = new FileOutputStream(target);
                AudioInputStream audioInputStream = new AudioInputStream(
                        new ByteArrayInputStream(data), af, data.length)) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
            out.flush();
        }
    }

    private void test()
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String string1 = String.class.newInstance();
        String string2 = (String) Class.forName(String.class.getName()).newInstance();
        AnotherOuterClass.class.newInstance();
        //        Constructor.newInstance("");
    }
}
