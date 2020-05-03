package com.kakaopay.payment.common;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class EncryptionUtils {

    private static final String key = "kakaoPay!@123456";
    private static String ips;
    private static Key keySpec;

    static {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
//        ips = key.substring(0, 16);
        ips = key;
        keySpec = secretKeySpec;
    }

    public static String encrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes()));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(key.getBytes()));
        byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes(StandardCharsets.UTF_8)));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8)));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(cipher.doFinal(byteStr), StandardCharsets.UTF_8);
    }
}
