package com.helper.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
    private static final String IV_STR = "EncryptionHandler";


    /************************************ Base64 Encryption Methods **********************************/

    public static String encode(String value) {
        return encode(value, StandardCharsets.UTF_8, Base64.DEFAULT);
    }
    public static String encode(String value, int decodeType) {
        return encode(value, StandardCharsets.UTF_8, decodeType);
    }
    // Sending side
    public static String encode(String value, Charset charsetName, int base64Type) {
        try {
            byte[] data = value.getBytes(charsetName);
            return Base64.encodeToString(data, base64Type);
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
    }

    public static String decode(String base64value) {
        return decode(base64value, StandardCharsets.UTF_8, Base64.DEFAULT);
    }
    public static String decode(String base64value, int decodeType) {
        return decode(base64value, StandardCharsets.UTF_8, decodeType);
    }
    // Receiving side
    public static String decode(String base64value, Charset charsetName, int base64Type) {
        try {
            byte[] data = Base64.decode(base64value, base64Type);
            return new String(data, charsetName);
        } catch (Exception e) {
            Logger.d(e.toString());
            return base64value;
        }
    }

    /************************************ AES Encryption Public Methods **********************************/

    /* Start Symmetric Encryption with AES */
    public static String encrypt(String keyStr, String enStr){
        try {
            byte[] bytes = encrypt(IV_STR, keyStr, enStr.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encode(bytes ,Base64.DEFAULT), StandardCharsets.UTF_8)
                    .replaceAll("\\\n","");
        } catch (Exception e) {
            Logger.d(e.toString());
            return enStr;
        }
    }

    public static String decrypt(String keyStr, String deStr){
        try {
            byte[] bytes = decrypt(IV_STR, keyStr, Base64.decode(deStr.getBytes(StandardCharsets.UTF_8),Base64.DEFAULT));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            Logger.d(e.toString());
            return deStr;
        }
    }

    /************************************ AES Encryption Methods *************************************/

    private static byte[] encrypt(String ivStr, String keyStr, byte[] bytes) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(ivStr.getBytes());
        byte[] ivBytes = md.digest();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(keyStr.getBytes());
        byte[] keyBytes = sha.digest();

        return encrypt(ivBytes, keyBytes, bytes);
    }

    private static byte[] encrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws Exception{
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return cipher.doFinal(bytes);
    }

    private static byte[] decrypt(String ivStr, String keyStr, byte[] bytes) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(ivStr.getBytes());
        byte[] ivBytes = md.digest();

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(keyStr.getBytes());
        byte[] keyBytes = sha.digest();

        return decrypt(ivBytes, keyBytes, bytes);
    }

    private static byte[] decrypt(byte[] ivBytes, byte[] keyBytes, byte[] bytes)  throws Exception{
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return cipher.doFinal(bytes);
    }
    /* End Symmetric Encryption with AES */
}
