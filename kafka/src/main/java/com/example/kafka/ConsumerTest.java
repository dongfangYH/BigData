package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-20 13:52
 **/
public class ConsumerTest {

    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "rddTest";

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args){
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "g11");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "c11");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Arrays.asList(TOPIC));
        List<PartitionInfo> partitionInfoList = consumer.partitionsFor(TOPIC);

        for (PartitionInfo partitionInfo : partitionInfoList){
            threadPool.execute(() -> {
                TopicPartition topicPartition = new TopicPartition(TOPIC, partitionInfo.partition());
                Properties conf = new Properties();
                conf.put(BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
                conf.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                conf.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                conf.put(ConsumerConfig.GROUP_ID_CONFIG, "cg1");
                conf.put(ConsumerConfig.CLIENT_ID_CONFIG, Thread.currentThread().getName());

                KafkaConsumer<String, String> c = new KafkaConsumer<>(conf);
                List<TopicPartition> topicPartitions = new ArrayList<>();
                topicPartitions.add(topicPartition);
                c.assign(topicPartitions);

                try{
                    while (true){
                        ConsumerRecords<String, String> records = c.poll(Duration.ofSeconds(5));
                        for (ConsumerRecord<String, String> record : records){
                            String value = record.value();
                            Long offset = record.offset();
                            System.out.println(Thread.currentThread().getName() + " -> partition: "+ partitionInfo.partition() +
                                    ", -> offset: " + offset + ", value -> " + value);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    c.close();
                }
            });
        }

    }
}
