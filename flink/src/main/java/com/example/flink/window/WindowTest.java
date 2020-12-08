package com.example.flink.window;

import com.example.flink.function.LineSplitter;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.flink.util.StringUtils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 14:23
 **/
public class WindowTest {
    public static void main(String[] args) throws Exception{
        final StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        streamEnv.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                1,
                org.apache.flink.api.common.time.Time.of(2, TimeUnit.SECONDS)
        ));

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");

        DataStreamSource<String> dataStream = streamEnv.addSource(new FlinkKafkaConsumer<>("word-event", new SimpleStringSchema(), properties));

        dataStream.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Long>> collector) throws Exception {
                if (!StringUtils.isNullOrWhitespaceOnly(s)){
                    for (String e : s.split(" ")){
                        collector.collect(new Tuple2<>(e, 1L));
                    }
                }
            }
        }).keyBy(0)
                // if number of element in the window more than 10, this window will trigger cal
                //.countWindow(10)
                // session window, if the stream haven't receive any data more than 5 sec, it will trigger the cal
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(5)))
                .sum(1)
                .print();

        streamEnv.execute("windowTest");
    }

}
