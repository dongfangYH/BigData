package com.example

import java.util
import java.util.{HashMap, Map, Properties, UUID}

import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer

object KafkaClientTest {

  def main(args: Array[String]): Unit = {
    val config = new util.HashMap[String, Object]
    config.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    config.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID.toString)
    config.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID.toString)
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    val consumer = new KafkaConsumer[String, String](config)

    //val topicList = new util.ArrayList[String]()
    //topicList.add("rddTest")
    val topicPartition = new TopicPartition("rddTest", 1)
    val tps = new util.ArrayList[TopicPartition]()
    tps.add(topicPartition)
    consumer.assign(tps)

    var i = 0
    while (true){
      val rit = consumer.poll(1).iterator()
      println("the " + i + " times consumes records;")
      while (rit.hasNext){
        val record = rit.next()
        println(record.value())
      }
      i += 1
    }
  }
}
