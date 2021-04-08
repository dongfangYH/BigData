package com.example.gof.behavior.command;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-11 10:01
 **/
public class CommandSelector {

    public static Command select(Request request){

        Command command = null;

        switch (request.getRequestType()){
            case GET_RESOURCE:
                command = new GetResourceCommand(request);
                break;
            case UPLOAD:
                command = new UploadCommand(request);
                break;
            case SYNC:
                command = new SyncCommand(request);
                break;
        }

        return command;
    }
}
