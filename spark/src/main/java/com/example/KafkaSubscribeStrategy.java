package com.example;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.NoOffsetForPartitionException;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * kafka订阅消费策略， 根据给定的主题与偏移信息创建kafka消费者
 **/
public class KafkaSubscribeStrategy<K, V> extends KafkaConsumerStrategy<K, V>{

    /**
     * 主题列表
     */
    private Collection<String> topics;

    /**
     * kafka配置参数
     */
    private Map<String, Object> kafkaParams;

    /**
     * 主题与偏移信息
     */
    private Map<TopicPartition, Long> offsets;

    public KafkaSubscribeStrategy(Collection<String> topics, Map<String, Object> kafkaParams,
                                  Map<TopicPartition, Long> offsets) {
        this.topics = topics;
        this.kafkaParams = kafkaParams;
        this.offsets = offsets;
    }

    public Consumer onStart(Map<TopicPartition, Long> currentOffsets) {
        KafkaConsumer<K, V> consumer = new KafkaConsumer<K, V>(kafkaParams);

        //订阅主题列表
        consumer.subscribe(topics);

        Map<TopicPartition, Long> toSeek = currentOffsets.isEmpty() ? offsets : currentOffsets;


        if (!toSeek.isEmpty()) {
            // poll will throw if no position, i.e. auto offset reset none and no explicit position
            // but cant seek to a position before poll, because poll is what gets subscription partitions
            // So, poll, suppress the first exception, then seek

            //auto.offset.reset  当消费者读取不要偏移时的策略
            // latest        , 从最新的开始读
            // none          , 抛出NoOffsetForPartitionException
            // earliest      , 从最旧的偏移开始读
            Object aor = kafkaParams.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG);

            boolean shouldSuppress = aor != null && ("NONE".equalsIgnoreCase(aor.toString()));
            try {
                consumer.poll(0);
            } catch (NoOffsetForPartitionException x){
                if (shouldSuppress){
                    System.out.println("Catching NoOffsetForPartitionException since " +
                            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG + " is none.  See KAFKA-3370");
                }

            }

            Iterator<Map.Entry<TopicPartition, Long>> iterator = toSeek.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<TopicPartition, Long> entry = iterator.next();
                TopicPartition topicPartition = entry.getKey();
                Long offset = entry.getValue();
                consumer.seek(topicPartition, offset);
            }

            // we've called poll, we must pause or next poll may consume messages and set position
            consumer.pause(consumer.assignment());
        }

        return consumer;
    }

}
