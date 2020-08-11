
package com.example

import java.sql.DriverManager

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

import scala.collection.mutable

object KafkaStreaming {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setAppName("kafkaSteamApp")
      .setMaster("yarn")  //local, mesos, yarn

    val ssc = new StreamingContext(sparkConf, Seconds(2))

    val topics = Array("eventStream")
    val kafkaParams = mutable.HashMap[String,String]()
    kafkaParams.put("bootstrap.servers", "localhost:9200")
    kafkaParams.put("group.id", "g1")
    kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val offsetMap= mutable.HashMap[TopicPartition, Long]()

    Class.forName("com.mysql.jdbc.Driver")
    val dbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/streaming?user=root&password=Aa123456$useSSL=false")
    val statement = dbConn.createStatement()
    val resultSet = statement.executeQuery("select * from streaming where topic = 'eventStream' and groupId = 'g1'")
    while (resultSet.next()){
      val partition = resultSet.getInt(3)
      val offset = resultSet.getLong(4)
      val topicPartition = new TopicPartition("eventStream", partition)
      offsetMap.put(topicPartition, offset)
    }

    var kafkaStream:InputDStream[ConsumerRecord[String, String]] = null
    if (offsetMap.isEmpty){
      kafkaStream = KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](topics, kafkaParams)
      )
    } else {
      kafkaStream = KafkaUtils.createDirectStream[String,String](
        ssc,
        LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String,String](topics, kafkaParams, offsetMap)
      )
    }

    kafkaStream.map(records => {
      records.offset();
    })


    //do something

    //....

    ssc.start()
    ssc.awaitTermination()
  }

}

