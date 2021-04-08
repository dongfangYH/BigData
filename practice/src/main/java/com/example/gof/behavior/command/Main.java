package com.example.gof.behavior.command;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yuanhang.liu@tcl.com
 * @description command pattern uses an object to wrap original request so that it can be handle by different types of
 *  command.
 * @date 2020-11-11 09:55
 **/
public class Main {
    public static void main(String[] args){

        RequestType[] types = {RequestType.GET_RESOURCE, RequestType.SYNC, RequestType.UPLOAD};

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++){
            Request request = new Request();
            request.setId(UUID.randomUUID().toString());
            request.setRequestType(types[i % 3]);
            request.setUserId("user-" + i);
            request.setData("data");

            Command command = CommandSelector.select(request);

            executorService.execute(() -> command.execute());
        }

        executorService.shutdown();
    }


}
