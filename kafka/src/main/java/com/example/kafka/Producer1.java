package com.example.kafka;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Future;

import static org.apache.kafka.clients.CommonClientConfigs.CLIENT_ID_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-03 13:34
 **/
public class Producer1 {

    public static final String BROKER_LIST = "localhost:9092";
    public static final String TOPIC = "mobile-event";
    public static final String[] packageName = {"Gallery", "Launcher", "TCL+", "WeChat", "MailBoss"};
    public static final int batch = 100;


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
        Gson gson = new Gson();
        Random random = new Random();

        for (;;){

            JsonObject object = new JsonObject();
            object.addProperty("id", UUID.randomUUID().toString());
            object.addProperty("happenTime", System.currentTimeMillis());
            object.addProperty("eventId", packageName[random.nextInt(packageName.length)]);
            object.addProperty("version", (random.nextInt(10) % (random.nextInt(5) + 1)) + 1);
            String value = gson.toJson(object);

            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC,  value);
            Future<RecordMetadata> result = producer.send(record);

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //关闭，释放资源
        //producer.close();
    }
}
