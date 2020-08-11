package com.example;

import org.apache.kafka.common.TopicPartition;
import org.apache.spark.Partition;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-28 15:09
 **/
public class KafkaRDDPartition implements Partition {

    private Integer index;
    private String topic;
    private Integer partition;
    private Long fromOffset;
    private Long untilOffset;

    public KafkaRDDPartition(Integer index) {
        this.index = index;
    }

    public int index() {
        return index;
    }

    public Long count(){
        return untilOffset - fromOffset;
    }

    public TopicPartition topicPartition(){
        return new TopicPartition(topic, partition);
    }
}
