package com.example.design_pattern.behavior.responsibilitychain;

public interface Handler {

    default String handle(String content){
        return handleInternal(content);
    }

    String handleInternal(String content);

}
