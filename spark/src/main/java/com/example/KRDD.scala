package com.example

import java.time.Duration
import java.util
import java.util.UUID

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkContext, TaskContext}

class KRDD[K, V](
  sc: SparkContext,
  val kafkaParams: util.Map[String, Object],
  val offsetMap: java.util.Map[TopicPartition, Long]
  ) extends RDD[ConsumerRecords[K, V]](sc, Nil){

  /**
   * 每个分片的计算逻辑
   * @param split
   * @param context
   * @return
   */
  override def compute(split: Partition, context: TaskContext): Iterator[ConsumerRecords[K, V]] = {
    val part = split.asInstanceOf[KRDDPartition]

    /**
     * 返回该分区可迭代的数据
     */
    new KRDDIterator[K, V](part, context, kafkaParams, 5)
  }

  /**
   * 获取RDD所有分区
   * @return
   */
  override protected def getPartitions: Array[Partition] = {
    if (offsetMap.isEmpty){
      val c = new KafkaConsumer[K, V](kafkaParams)
      val topics = kafkaParams.get("topics").asInstanceOf[String].split(",")
      val topicList = new util.ArrayList[String]()
      topics.foreach(t => topicList.add(t))
      c.subscribe(topicList)

      val it = topicList.iterator()

      while (it.hasNext){
        val tp = it.next()
        val it2 = c.partitionsFor(tp).iterator()
        while (it2.hasNext){
          val partitionInfo = it2.next()
          val topicPartition = new TopicPartition(tp, partitionInfo.partition())
          offsetMap.put(topicPartition, 0)
        }
      }

    }

    val it =offsetMap.entrySet().iterator();
    var idx = 0
    val arr = new Array[Partition](offsetMap.size())

    while (it.hasNext){
      val entry = it.next()
      arr(idx) = new KRDDPartition(idx, entry.getKey.topic(), entry.getKey.partition(), entry.getValue);
      idx += 1
    }

    arr
  }

}

private class KRDDIterator[K, V]
(
  part: KRDDPartition,
  context: TaskContext,
  kafkaParams: util.Map[String, Object],
  pollTimeout: Long
) extends Iterator[ConsumerRecords[K, V]] {

  val groupId = kafkaParams.get(ConsumerConfig.GROUP_ID_CONFIG).asInstanceOf[String]

  kafkaParams.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString)
  val consumer = new KafkaConsumer[K, V](kafkaParams)

  {
    val parts = new util.ArrayList[TopicPartition]()
    parts.add(part.topicPartition())
    consumer.assign(parts)
    //consumer.seek(part.topicPartition(), part.fromOffset)
  }

  //用list缓存数据
  @volatile var buffer = new util.concurrent.ConcurrentLinkedQueue[ConsumerRecords[K, V]]()
  private var exitNextTime = false

  override def hasNext: Boolean = {
    val records = consumer.poll(pollTimeout)
    buffer.add(records)

    if (exitNextTime){
      return false
    }

    if (!exitNextTime){
      exitNextTime = true
    }

    !buffer.isEmpty
  }

  override def next(): ConsumerRecords[K, V] = {
    //println("records : " + buffer.size())
    val record = buffer.poll()
    record
  }
}