package com.example.gof.behavior.responsibilitychain;


/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-12 16:49
 **/
public class ViolentWordHandler implements Handler {
    @Override
    public String handleInternal(String content) {
        if (content.contains("kill")){
            content = content.replaceAll("kill", "***");
        }
        return content;
    }
}
