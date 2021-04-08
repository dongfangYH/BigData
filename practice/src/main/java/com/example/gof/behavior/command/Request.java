package com.example.gof.behavior.command;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 09:55
 **/
public class Request {

    private String id;

    private String userId;

    private RequestType requestType;

    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
