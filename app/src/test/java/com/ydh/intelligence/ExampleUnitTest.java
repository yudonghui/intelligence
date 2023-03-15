package com.ydh.intelligence;

import com.ydh.intelligence.aop.AnnotationAnalysis;
import com.ydh.intelligence.aop.ApiToken;
import com.ydh.intelligence.aop.UserInfo;
import com.ydh.intelligence.utils.LogUtils;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws UnsupportedEncodingException {


        String string = "0987654321yudonghui";
        String md5 = md5(string);
        System.out.println("md5加密后: " + md5);
        System.out.println("md5再加密后: " + convertMD5(md5));
        System.out.println("md5解密后: " + convertMD5(convertMD5(md5)));
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        final String text = "yusk-7FpVpQSashXL8dPCB1HqT3BlbkFJ5owdLqjJX88C2rd48pdC";
        final byte[] textByte = text.getBytes("UTF-8");
        //编码
        final String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
        //解码
        System.out.println(new String(decoder.decode(encodedText), "UTF-8").substring(2));

    }



    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 可逆的的加密解密方法；两次是解密，一次是加密
     *
     * @param inStr
     * @return
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

}