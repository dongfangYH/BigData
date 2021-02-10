package com.example.flink.test;

import com.example.flink.entity.PhoneEvent;
import com.example.flink.function.PhoneOfflineFunction;
import com.example.flink.source.PhoneEventGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Clock;
import java.time.Instant;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2021-01-28 16:51
 **/
@Slf4j
public class PhoneOfflineTest {

    public static void main(String[] args) throws Exception{
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        final StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        streamEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        streamEnv.enableCheckpointing(60000L, CheckpointingMode.EXACTLY_ONCE);
        //streamEnv.setParallelism(1);
        /*streamEnv.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                1,
                org.apache.flink.api.common.time.Time.of(2, TimeUnit.SECONDS)
        ));*/

        DataStream<PhoneEvent> offlinePhones = streamEnv.addSource(new PhoneEventGenerator())
                .assignTimestampsAndWatermarks(WatermarkStrategy.forGenerator((WatermarkGeneratorSupplier<PhoneEvent>) context -> new WatermarkGenerator<PhoneEvent>() {
                    @Override
                    public void onEvent(PhoneEvent phoneEvent, long l, WatermarkOutput watermarkOutput) {
                        watermarkOutput.emitWatermark(new Watermark(phoneEvent.getEventTime()));
                    }

                    @Override
                    public void onPeriodicEmit(WatermarkOutput watermarkOutput) {
                        watermarkOutput.emitWatermark(new Watermark(System.currentTimeMillis()));
                    }
                }))
                .keyBy(PhoneEvent::getId)
                .process(new PhoneOfflineFunction());

        offlinePhones.print();

        streamEnv.execute("PhoneOfflineTest");
    }
}
