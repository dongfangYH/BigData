package com.example

import org.apache.spark.{SparkConf, SparkContext}

object RDDTest2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
                  .setAppName("test")
                  .setMaster("local[2]")
                  //.set("spark.hadoop.yarn.resourcemanager.address", "localhost")
    val sc = new SparkContext(conf)

    val rdd1 = sc.makeRDD(
      List(("Ajax", 3), ("Java", 2), ("Hadoop", 10), ("Kafka", 2), ("Hive", 20)), 3)
    val rdd2 = sc.makeRDD(
      List(("kafka", 10), ("HBase", 10), ("Hadoop", 2), ("Presto", 3), ("Spark", 30)), 2)

    rdd1.join(rdd2).foreachPartition(it => {
      if (it.hasNext){
        val e = it.next()
        println(e._1 + " : " + (e._2._1 + e._2._2))
      }
    })
  }
}
