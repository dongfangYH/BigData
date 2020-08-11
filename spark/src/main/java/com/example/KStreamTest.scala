package com.example

import java.util
import java.util.UUID

import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object KStreamTest {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("kafkaSteamApp")
      .setMaster("local[3]")

    val kafkaConfig = new java.util.HashMap[String, Object]()
    kafkaConfig.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString)
    kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    kafkaConfig.put("topics", "rddTest")
    val offsetMap = new util.HashMap[TopicPartition, Long]()

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    new KInputStream[String, String](ssc, kafkaConfig, offsetMap)
      .flatMap(e => {
        val it = e.iterator()
        val result = new ListBuffer[String]
        while (it.hasNext){
          val r = it.next()
          println(r.key() + "" + r.value())
          result.+(r.value())
        }
        result.toList
      }).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
