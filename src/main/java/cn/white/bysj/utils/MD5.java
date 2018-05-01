package cn.white.bysj.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create by @author white
 *
 * @date 2017-12-27 22:06
 */
public class MD5 {

    //将明文进行加密
    public static String md5(String src) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] output = md.digest(src.getBytes());
        String s = Base64.encodeBase64String(output);
        return s;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String out = md5("111111");
        System.out.println(out);
    }

}
