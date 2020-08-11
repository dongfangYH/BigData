package com.example

import org.apache.kafka.common.TopicPartition
import org.apache.spark.Partition

private[example]
class KRDDPartition(val index: Int,
                    val topic: String,
                    val partition: Int,
                    val fromOffset: Long
                  ) extends Partition {

  /** Kafka TopicPartition object, for convenience */
  def topicPartition(): TopicPartition = new TopicPartition(topic, partition)
}
