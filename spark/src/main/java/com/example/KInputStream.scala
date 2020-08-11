package com.example

import org.apache.kafka.clients.consumer.{Consumer, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.{StreamingContext, Time}
import org.apache.spark.streaming.dstream.InputDStream

class KInputStream[K, V](
   ssc: StreamingContext,
   val kafkaParams: java.util.Map[String, Object],
   val offsetMap: java.util.Map[TopicPartition, Long]
) extends InputDStream[ConsumerRecords[K, V]](ssc){

   @transient private var kc: Consumer[K, V] = null

   // stream start
   override def start(): Unit = {
      kc = new KafkaConsumer[K, V](kafkaParams)
   }

   // steam stop
   override def stop(): Unit = {
      if (kc != null){
          kc.close()
      }
   }

   // compute batch rdd
   override def compute(validTime: Time): Option[KRDD[K, V]] = {
      println("----- kafka input stream start ----- " + validTime.toString())
      val kafkaRDD = new KRDD[K, V](ssc.sparkContext, kafkaParams, offsetMap)
      Some(kafkaRDD)
   }
}
