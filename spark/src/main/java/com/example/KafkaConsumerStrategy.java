package com.example;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

/**
 * kafka消费策略，供子类实现
 **/
public abstract class KafkaConsumerStrategy<K, V> {

    /**
     * 提供executor使用的kafka参数
     */
    public Map<String, Object> executorKafkaParams;

    /**
     * 根据分区偏移信息创建kafka消费者，子类实现
     * @param currentOffsets
     * @return
     */
    public abstract Consumer<K, V> onStart(Map<TopicPartition, Long> currentOffsets);
}
