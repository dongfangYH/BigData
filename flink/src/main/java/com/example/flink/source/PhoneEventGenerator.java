package com.example.flink.source;

import com.example.flink.entity.PhoneEvent;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2021-01-28 15:22
 **/
public class PhoneEventGenerator implements SourceFunction<PhoneEvent> {

    private volatile boolean running = true;
    private final LongAdder adder = new LongAdder();
    private final LongAdder idGen = new LongAdder();

    @Override
    public void run(SourceContext<PhoneEvent> sourceContext) throws Exception {
        idGen.increment();
        while (running){
            PhoneEvent phoneEvent = new PhoneEvent();
            phoneEvent.setId(idGen.longValue());
            phoneEvent.setPhone("iPhone Xs");
            phoneEvent.setTimestamp(System.currentTimeMillis());
            //System.out.println("produce phone event -> " + phoneEvent.toString());
            sourceContext.collect(phoneEvent);
            sleep();
        }
    }

    private void sleep() throws Exception{
        adder.increment();
        if (adder.longValue() > 5){
            adder.reset();
            idGen.increment();
        }
        Thread.sleep(2000L);
    }

    @Override
    public void cancel() {
        running = false;
    }
}
