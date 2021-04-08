package com.example.gof.behavior.command;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:03
 **/
public abstract class AbstractCommand implements Command{

    private final Request request;

    protected AbstractCommand(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }
}
