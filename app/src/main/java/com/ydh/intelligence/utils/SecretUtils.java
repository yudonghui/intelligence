package com.ydh.intelligence.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Date:2023/2/10
 * Time:9:49
 * author:ydh
 */
public class SecretUtils {
    //加密
    public static String encode(String text) {
        String string = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.Encoder encoder = Base64.getEncoder();
            string = encoder.encodeToString(text.getBytes());
        }
        return string;
    }

    //解密
    public static String decode(String text) {
        String string = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Base64.Decoder decoder = Base64.getDecoder();
            try {
                string = new String(decoder.decode(text), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return string;
    }
}
