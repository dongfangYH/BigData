package com.example.net.codec;

import java.util.List;
import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:11
 **/
public class JsonReq {

    private String id;
    private String content;
    private String signature;
    private List<String> tags;

    public JsonReq() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "JsonReq{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", signature='" + signature + '\'' +
                ", tags=" + tags +
                '}';
    }
}
