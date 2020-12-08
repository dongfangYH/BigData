package com.example.flink.window.source;

import com.example.flink.entity.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.util.Random;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 16:58
 **/
@Slf4j
public class MySourceFunction extends RichSourceFunction<MyEvent> {

    private volatile boolean running = true;
    private Random random = new Random();

    @Override
    public void run(SourceContext<MyEvent> sourceContext) throws Exception {
        while (running){
            int id = random.nextInt(100);
            long eventTime = System.currentTimeMillis() - ((random.nextInt(5) + 1) * 1000L);
            sourceContext.collect(new MyEvent(id, eventTime));
            try{
                Thread.sleep(random.nextInt(100));
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void cancel() {
        running = false;
        // help gc ?
        random = null;
    }
}
