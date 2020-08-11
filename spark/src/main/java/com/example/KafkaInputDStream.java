package com.example;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.rdd.RDD;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.dstream.InputDStream;
import org.glassfish.hk2.api.messaging.Topic;
import scala.Option;
import scala.reflect.ClassTag;

import java.util.*;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-28 11:18
 **/
public class KafkaInputDStream<K, V> extends InputDStream<ConsumerRecord<K, V>> {

    private  Consumer<K, V> consumer;
    private KafkaConsumerStrategy<K,V> consumerStrategy;
    private Map<TopicPartition, Long> currentOffsets = new HashMap<TopicPartition, Long>();


    public KafkaInputDStream(StreamingContext ssc, ClassTag classTag) {
        super(ssc, classTag);
    }


    /**
     * 创建kafka 消费者
     * @return
     */
    private synchronized Consumer<K, V> createConsumer(){
        if (consumer == null){
            this.consumer = consumerStrategy.onStart(currentOffsets);
        }
        return consumer;
    }

    /**
     * input dstream 抽象类定义的方法，子类继承实现，用于开启接收消息
     */
    public void start() {
        Consumer<K, V> c = createConsumer();
        paranoidPoll(c);
        // 如果current offset为空，则将消费者获取到的偏移值写进去
        if (currentOffsets.isEmpty()){
            Iterator<TopicPartition> iterator = c.assignment().iterator();
            while (iterator.hasNext()){
                TopicPartition tp = iterator.next();
                currentOffsets.put(tp, c.position(tp));
            }
        }

        // don't actually want to consume any messages, so pause all partitions
        c.pause(currentOffsets.keySet());
    }

    /**
     * input dstream 抽象类定义的方法，子类继承实现，用于关闭接收消息
     */
    public void stop() {
        if (consumer != null){
            consumer.close();
        }
    }

    public Option<RDD<ConsumerRecord<K, V>>> compute(Time validTime) {


        return null;
    }

    private void paranoidPoll(Consumer<K, V> c){
        ConsumerRecords<K, V> consumerRecords = c.poll(0);
        if (!consumerRecords.isEmpty()) {
            // position should be minimum offset per topic partition

            Iterator<ConsumerRecord<K, V>> iterator = consumerRecords.iterator();
            Map<TopicPartition, Long> minimunOffsetMap = new HashMap<TopicPartition, Long>();
            while (iterator.hasNext()){
                ConsumerRecord<K, V> record =  iterator.next();
                TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                Long offset = Math.min(minimunOffsetMap.get(tp), record.offset());
                minimunOffsetMap.put(tp, offset);
            }

            Iterator<Map.Entry<TopicPartition, Long>> offsetIter = minimunOffsetMap.entrySet().iterator();

            while (offsetIter.hasNext()){
                System.out.println("poll(0) returned messages, seeking $tp to $off to compensate");
                Map.Entry<TopicPartition, Long> entry = offsetIter.next();
                TopicPartition tp = entry.getKey();
                Long offset = entry.getValue();
                c.seek(tp, offset);
            }

        }
    }

    /**
     * Returns the latest (highest) available offsets, taking new partitions into account.
     */
    protected Map<TopicPartition, Long> latestOffsets(){
        Consumer<K, V> c = consumer;
        paranoidPoll(c);
        Set<TopicPartition> parts = c.assignment();

        // make sure new partitions are reflected in currentOffsets
        Set<TopicPartition> newPartitions = getDiff(parts, currentOffsets.keySet());

        Map<TopicPartition, Long> newPartitionOffsetMap = new HashMap<TopicPartition, Long>();
        for (TopicPartition tp : newPartitions){
           newPartitionOffsetMap.put(tp, c.position(tp));
        }

        // position for new partitions determined by auto.offset.reset if no commit
        currentOffsets.putAll(newPartitionOffsetMap);
        // don't want to consume messages, so pause
        c.pause(newPartitions);
        // find latest available offsets
        c.seekToEnd(currentOffsets.keySet());

        Map<TopicPartition, Long> result = new HashMap<TopicPartition, Long>();
        for (TopicPartition tp : parts){
            result.put(tp, c.position(tp));
        }

        return result;
    }

    /**
     * 得到left比right多的部分
     * @param left
     * @param right
     * @return
     */
    private Set<TopicPartition> getDiff(Set<TopicPartition> left, Set<TopicPartition> right) {
        Set<TopicPartition> result = new HashSet<TopicPartition>();
        for (TopicPartition tp : left){
            if (!right.contains(tp)){
                result.add(tp);
            }
        }
        return result;
    }
}
