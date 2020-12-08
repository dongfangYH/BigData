package com.example.flink.function;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-08 14:29
 **/
@Slf4j
public class LineSplitter implements FlatMapFunction<String, Tuple2<Long, String>> {

    @Override
    public void flatMap(String s, Collector<Tuple2<Long, String>> collector) throws Exception {
        String[] tokens = s.split(" ");
        log.info("receive : " + s);
        if (tokens.length >= 2 && isValidLong(tokens[0])){
            collector.collect(new Tuple2<>(Long.valueOf(tokens[0]), tokens[1]));
        }
    }

    private boolean isValidLong(String token) {
        try{
            Long.parseLong(token);
            return true;
        }catch (NumberFormatException e){
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
