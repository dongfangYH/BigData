package com.example.net;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-09-28 17:15
 **/
@org.msgpack.annotation.Message
public class Response {
    private Integer type;
    private String script;
    private String content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", script='" + script + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
