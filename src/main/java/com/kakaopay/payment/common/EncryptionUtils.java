package com.kakaopay.payment.common;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

// TODO: pojo 생각 해보자.. 아니면 bean으로?
public class EncryptionUtils {

    private static final String key = "kakaoPay!@123456";
    private static String ips;
    private static Key keySpec;

    private static void init() throws Exception {
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(b, 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        ips = key.substring(0, 16);
        keySpec = secretKeySpec;
    }

    public static String encrypt(String str) throws Exception {
        if (ips == null || keySpec == null) {
            init();
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes()));
        byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decrypt(String str) throws Exception {
        if (ips == null || keySpec == null) {
            init();
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ips.getBytes("UTF-8")));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(cipher.doFinal(byteStr), StandardCharsets.UTF_8);
    }
}
