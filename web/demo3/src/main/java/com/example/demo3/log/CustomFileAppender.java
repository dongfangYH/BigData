package com.example.demo3.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.spi.DeferredProcessingAware;
import ch.qos.logback.core.status.ErrorStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2021-01-18 14:47
 **/
public class CustomFileAppender extends FileAppender<ILoggingEvent> {

    private OutputStream wos;

    public CustomFileAppender() {

        try{
            wos = new FileOutputStream(new File("/home/lmh/log"), true);
        }catch (Exception e){

        }

    }

    @Override
    protected void subAppend(ILoggingEvent event) {
        try {
            if (event instanceof DeferredProcessingAware) {
                ((DeferredProcessingAware) event).prepareForDeferredProcessing();
            }
            byte[] byteArray = this.encoder.encode(event);
            writeBytes(wos, byteArray);

        } catch (IOException ioe) {
            this.started = false;
            addStatus(new ErrorStatus("IO failure in appender", this, ioe));
        }
    }

    private void writeBytes(OutputStream os, byte[] byteArray) throws IOException {
        if (byteArray == null || byteArray.length == 0)
            return;

        lock.lock();
        try {
            os.write(byteArray);
            if (isImmediateFlush()) {
                os.flush();
            }
        } finally {
            lock.unlock();
        }
    }
}
