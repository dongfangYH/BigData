package com.example

import java.util
import java.util.UUID

import org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords}
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks

object RDDTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
                  .setAppName("test")
                  //.setMaster("yarn")
                  //.set("spark.hadoop.yarn.resourcemanager.address", "localhost")
    val sc = new SparkContext(conf)


    val kafkaConfig = new java.util.HashMap[String, Object]()
    kafkaConfig.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString)
    kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    kafkaConfig.put("topics", "rddTest")
    val offsetMap = new util.HashMap[TopicPartition, Long]()
    new KRDD[String, String](sc, kafkaConfig, offsetMap).flatMap(
      rds => {
        val it = rds.iterator()
        val list = new scala.collection.mutable.ListBuffer[String]
        while (it.hasNext){
          val r = it.next()
          list.+=(r.value())
        }
        list
      }
    ).map(s => (s, 1))
      .reduceByKey((k1,k2) => k1+k2)
      .saveAsTextFile("/result")

  }
}
