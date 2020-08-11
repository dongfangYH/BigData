package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-20 13:52
 **/
public class ConsumerPartitionTest {

    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "rddTest";

    public static void main(String[] args){
        Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "cg1");
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, "c-1");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Arrays.asList(TOPIC));

        //TopicPartition topicPartition = new TopicPartition(TOPIC, 0);

        //List<TopicPartition> topicPartitions = new ArrayList<>();
        //topicPartitions.add(topicPartition);
        //consumer.assign(topicPartitions);

        try{
            while (true){
                //consumer.seek(topicPartition, 941);
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, String> record : records){
                    String value = record.value();
                    Long offset = record.offset();
                    System.out.println(Thread.currentThread().getName() + " -> partition: 0, -> offset: " + offset + ", value -> " + value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            consumer.close();
        }


    }
}
