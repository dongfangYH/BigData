package com.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.Dependency;
import org.apache.spark.Partition;
import org.apache.spark.SparkContext;
import org.apache.spark.TaskContext;
import org.apache.spark.rdd.RDD;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.reflect.ClassTag;

/**
 * @author yuanhang.liu@tcl.com
 * @description KafkaRDD
 * @date 2020-04-28 12:09
 **/
public class KafkaRDD<K, V> extends RDD<ConsumerRecord<K, V>> {

    public KafkaRDD(SparkContext sc, Seq<Dependency<?>> deps, ClassTag<ConsumerRecord<K, V>> classTag) {
        super(sc, deps, classTag);
    }

    public Iterator<ConsumerRecord<K, V>> compute(Partition split, TaskContext context) {
        split.index();
        return null;
    }

    public Partition[] getPartitions() {
        return new Partition[0];
    }
}
