package com.hughes.algorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.hash.Hashing;

/**
 * @author hugheslou
 * Created on 2018/5/24.
 */
public class Sha256 {

    private static final int FF = 0xff;

    public static void main(String[] args) throws Exception {
        String test = "123";
        // MessageDigest Class in Java
        byte[] encodedHash = MessageDigest.getInstance("SHA-256")
                .digest(test.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(encodedHash));
        System.out.println(bytesToHex(encodedHash));
        // Guava Library
        System.out.println(Hashing.sha256().hashString(test, StandardCharsets.UTF_8).toString());
        // Apache Commons Codecs
        System.out.println(DigestUtils.sha256Hex(test));
        // bcprov-jdk15on, Hex.encode
        System.exit(0);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(FF & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}