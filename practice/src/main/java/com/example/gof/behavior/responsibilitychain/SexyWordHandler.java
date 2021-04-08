package com.example.gof.behavior.responsibilitychain;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-12 16:35
 **/
public class SexyWordHandler implements Handler{

    @Override
    public String handleInternal(String content) {
        if (content.contains("nipple")){
            content = content.replaceAll("nipple", "***");
        }
        return content;
    }
}
