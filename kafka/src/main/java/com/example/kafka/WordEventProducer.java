package com.example.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;

import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-18 17:44
 **/
public class WordEventProducer {

    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "word-event";
    public static final String[] WORDS = {
            "phone", "TCL", "baidu", "kafka", "flink", "source", "collector",
            "jaw", "chin", "tear", "name", "jack", "link", "conspicuous", "spark",
            "java", "google", "python"
    };

    public static Properties config(){
        Properties config = new Properties();
        config.put(BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
        config.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(REQUEST_TIMEOUT_MS_CONFIG, "1000");
        config.put(DELIVERY_TIMEOUT_MS_CONFIG, "1000");
        //32m
        config.put(BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024L);
        config.put(CLIENT_ID_CONFIG, "c1");
        //重试一次
        config.put(RETRIES_CONFIG, "1");
        return config;
    }

    public static void main(String[] args) throws Exception{

        Properties config = config();
        //config.put(INTERCEPTOR_CLASSES_CONFIG, "com.example.kafka.LogInterceptor");
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(config);
        Random random = new Random();
        for (;;){
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC,WORDS[random.nextInt(WORDS.length)]);
            try {
                producer.send(record);
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //关闭，释放资源
        //producer.close();
    }

}
