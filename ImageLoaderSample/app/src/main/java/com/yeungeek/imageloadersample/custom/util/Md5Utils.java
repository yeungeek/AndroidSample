package com.yeungeek.imageloadersample.custom.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yeungeek on 2016/2/17.
 */
public class Md5Utils {
    private static MessageDigest digest = null;

    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getMd5(final String key) {
        String cacheKey;
        if (null == digest) {
            return String.valueOf(key.hashCode());
        }

        digest.update(key.getBytes());
        cacheKey = bytesToHexString(digest.digest());
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
