package com.example.design_pattern.behavior.responsibilitychain;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-10-12 16:32
 **/
public class SensitiveContentFilterChain {

    private List<Handler> handlerList = new LinkedList<>();

    public void addHandler(Handler handler){
        handlerList.add(handler);
    }

    public String handle(String content){
        for (Handler handler : handlerList){
            content = handler.handle(content);
        }
        return content;
    }
}
