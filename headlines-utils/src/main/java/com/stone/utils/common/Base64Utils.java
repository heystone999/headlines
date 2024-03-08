package com.stone.utils.common;


import java.util.Base64;

public class Base64Utils {

    /**
     * 解码
     *
     * @param base64
     * @return
     */
    public static byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }


    /**
     * 编码
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}