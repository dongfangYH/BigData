package com.example.net.codec;

import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-30 10:13
 **/
public class JsonResp {

    private Integer type;
    private String content;
    private Map<String, Object> extra;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "JsonResp{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", extra=" + extra +
                '}';
    }
}
