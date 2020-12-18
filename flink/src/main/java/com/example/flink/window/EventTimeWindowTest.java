package com.example.flink.window;

import com.example.flink.entity.PhoneEvent;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 14:23
 **/
@Slf4j
public class EventTimeWindowTest {

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setParallelism(1);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                1,
                org.apache.flink.api.common.time.Time.of(2, TimeUnit.SECONDS)
        ));

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "t2");

        DataStreamSource<String> dataStream = env.addSource(new FlinkKafkaConsumer<>("event-source", new SimpleStringSchema(), properties));
        dataStream.flatMap(new FlatMapFunction<String, PhoneEvent>() {
            @Override
            public void flatMap(String s, Collector<PhoneEvent> collector) throws Exception {
                if (!StringUtils.isNullOrWhitespaceOnly(s)){
                    try{
                        collector.collect(gson.fromJson(s, PhoneEvent.class));
                    }catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }).assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<PhoneEvent>() {
            private long currentTimestamp = -1L;
            private final long maxLag = 5000;

            /**
             * 周期性地生成watermarks
             * 前最新事件时间产生Watermarks时间戳，记为X，进入到Flink系统中的数据事件时间，记为Y，
             * 如果Y < X，则代表Watermark X时间戳之前的所有事件均已到达，
             * 同时Window的End Time大于Watermark，则触发窗口计算结果并输出
             * @return
             */
            @Nullable
            @Override
            public Watermark getCurrentWatermark() {
                return new Watermark(currentTimestamp - maxLag);
            }

            /**
             * [1, 2]
             * @param phoneEvent
             * @param l
             * @return
             */
            @Override
            public long extractTimestamp(PhoneEvent phoneEvent, long l) {
                long eventTime = phoneEvent.getTimestamp();
                currentTimestamp = Math.max(currentTimestamp, eventTime);
                return eventTime;
            }
        })
                .keyBy(PhoneEvent::getPhone)
                .window(TumblingEventTimeWindows.of(Time.seconds(2)))
                .apply(new WindowFunction<PhoneEvent, PhoneEvent, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow timeWindow, Iterable<PhoneEvent> iterable, Collector<PhoneEvent> collector) throws Exception {
                        System.out.println("window[" + timeWindow.getStart() + ", "  + timeWindow.getEnd() + "]");
                        for (PhoneEvent phoneEvent : iterable){
                            collector.collect(phoneEvent);
                        }
                    }
                })
                .print();

        env.execute("EventTimeWindowTestJob");
    }

}
