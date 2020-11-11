package com.example.design_pattern.behavior.command;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:01
 **/
public class SyncCommand extends AbstractCommand{

    protected SyncCommand(Request request) {
        super(request);
    }

    @Override
    public void execute() {
        Request request = getRequest();
        System.out.println("execute " + request.getUserId() + "'s " + request.getRequestType() + " request, data :" + request.getData());
    }
}
