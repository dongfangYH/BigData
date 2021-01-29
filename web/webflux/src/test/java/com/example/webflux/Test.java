package com.example.webflux;

import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-20 11:37
 **/
public class Test {

    public static void main(String[] args) {
        String secret = "google.com";
        long exp = (System.currentTimeMillis() / 1000) + 300;
        String encodeStr = Base64Utils.encodeToUrlSafeString(DigestUtils.md5Digest((secret + "30c1cf9801d2402dbf309c242837b41d.apk" + exp).getBytes(Charset.forName("UTF-8"))));
        System.out.println(encodeStr);
    }
}
