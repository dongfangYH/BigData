package com.example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}

object ItemBaseMatrix {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("item_matrix").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("file:///soft/spark/data/mllib/als/test.data")
    val parsedData = data.map(_.split(",") match {
      case Array(user, item, rate) => MatrixEntry(
        user.toLong - 1, item.toLong - 1, rate.toDouble
      )
    })

    val ratings = new CoordinateMatrix(parsedData)
    val similarities = ratings.toRowMatrix().columnSimilarities(0.1)

    val ratingsOfItem1 = ratings.transpose().toRowMatrix().rows.collect()(0).toArray
    val avgRatingOfItem1 = ratingsOfItem1.sum / ratingsOfItem1.size
    val ratingsOfUser1 = ratings.toRowMatrix().rows.collect()(0).toArray.drop(1)
    val weights = similarities.entries.filter(_.i == 0).sortBy(_.j).map(_.value).collect()

    val weightedR = (0 to 2).map(t => weights(t) * ratingsOfUser1(t)).sum / weights.sum
    println("Rating of user 1 towards item 1 is : " + (avgRatingOfItem1 + weightedR))
  }
}
