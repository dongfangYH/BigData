package com.example.flink.function;

import com.example.flink.entity.PhoneEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimerService;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2021-01-28 15:55
 **/
@Slf4j
public class PhoneOfflineFunction extends KeyedProcessFunction<Long, PhoneEvent, PhoneEvent> {

    private ValueState<PhoneEvent> eventState;

    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<PhoneEvent> stateDescriptor =
                new ValueStateDescriptor<>("eventState", PhoneEvent.class);
        eventState = getRuntimeContext().getState(stateDescriptor);
    }

    @Override
    public void processElement(PhoneEvent phoneEvent, Context context, Collector<PhoneEvent> collector) throws Exception {
        System.out.println("receive phone event <- " + phoneEvent.toString());
        if (eventState.value() == null || phoneEvent.getTimestamp() > eventState.value().getTimestamp()){
            updateStateAndRegTimer(phoneEvent, context.timerService());
        }

    }

    public void updateStateAndRegTimer(PhoneEvent phoneEvent, TimerService timerService) throws Exception{
        eventState.update(phoneEvent);
        timerService.registerEventTimeTimer(phoneEvent.getEventTime() + 10000L);
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<PhoneEvent> out) throws Exception {
        if (Math.abs(eventState.value().getEventTime() - System.currentTimeMillis()) > 10000L){
            out.collect(eventState.value());
            eventState.clear();
        }
    }
}
