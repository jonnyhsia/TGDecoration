package com.tugou.decoration.model;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;

public class Algorithm {

    public static String md5(@NonNull final String plainText) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(plainText.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        return Algorithm.bin2Hex(hash);
    }

    public static String bin2Hex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * HMAC256 Hash
     *
     * @param plainText String 明文
     * @param secretKey String 密钥
     * @return String 可为null
     */
    public static String hmac256(@NonNull final String plainText, @NonNull final String secretKey) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF8"), "HmacSHA256");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] cipherText = mac.doFinal(plainText.getBytes("utf-8"));

            String hexString = Algorithm.bin2Hex(cipherText);

            return Algorithm.base64Encode(hexString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String randomString(int length) {

        if (length == 0) {
            return "";
        }

        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Random random = new Random();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(base.charAt(random.nextInt(base.length())));
        }

        return stringBuilder.toString();
    }

    public static String base64Encode(@NonNull final String plainText) {
        return Base64.encodeToString(plainText.getBytes(), Base64.NO_WRAP);
    }
}
