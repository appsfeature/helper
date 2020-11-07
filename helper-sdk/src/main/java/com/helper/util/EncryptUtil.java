package com.helper.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class EncryptUtil {


    public static String encode(String value) {
        return encode(value, "UTF-8", Base64.DEFAULT);
    }
    public static String encode(String value, int decodeType) {
        return encode(value, "UTF-8", decodeType);
    }
    // Sending side
    public static String encode(String value, String charsetName, int base64Type) {
        try {
            byte[] data = value.getBytes(charsetName);
            return Base64.encodeToString(data, base64Type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static String decode(String base64value) {
        return decode(base64value, "UTF-8", Base64.DEFAULT);
    }
    public static String decode(String base64value, int decodeType) {
        return decode(base64value, "UTF-8", decodeType);
    }
    // Receiving side
    public static String decode(String base64value,String charsetName, int base64Type) {
        try {
            byte[] data = Base64.decode(base64value, base64Type);
            return new String(data, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return base64value;
        }
    }
}
