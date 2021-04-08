package com.example.gof.behavior.command;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:00
 **/
public class GetResourceCommand extends AbstractCommand{

    protected GetResourceCommand(Request request) {
        super(request);
    }

    @Override
    public void execute() {
        Request request = getRequest();
        System.out.println("execute " + request.getUserId() + "'s " + request.getRequestType() + " request, data :" + request.getData());
    }
}
