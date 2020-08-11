package com.example.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-20 09:42
 **/
public class LogInterceptor implements ProducerInterceptor {
    public ProducerRecord onSend(ProducerRecord record) {
        System.out.println("key: " + record.key() + " ,partition: " + record.partition() +
                " ,timestamp: " + record.timestamp() + " ,value: " + record.value());
        return record;
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}
