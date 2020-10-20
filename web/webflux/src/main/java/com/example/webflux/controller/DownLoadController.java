/*
package com.example.webflux.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

*/
/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-20 14:08
 **//*

@RestController
public class DownLoadController {

    @Value("${target.host}")
    private String host;

    @GetMapping("/download")
    public String download(){
        String secret = "google.com";
        long exp = (System.currentTimeMillis()/1000) + 300;
        String path = "/static/30c1cf9801d2402dbf309c242837b41d.apk";
        String encodeStr = Base64Utils.encodeToUrlSafeString(
                DigestUtils.md5Digest((secret + path + exp).getBytes(Charset.forName("UTF-8")))
        );

        String content = "<a href=http://"+host+"/static/30c1cf9801d2402dbf309c242837b41d.apk?st="+encodeStr+"&e="+exp+">WeComics.apk</a>";

        return content;
    }
}
*/
