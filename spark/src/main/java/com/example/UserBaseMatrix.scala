package com.example

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}

object UserBaseMatrix {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("user_matrix").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("file:///soft/spark/data/mllib/als/test.data")
    val parsedData = data.map(_.split(",") match {
      case Array(user, item, rate) => MatrixEntry(
        user.toLong - 1, item.toLong - 1, rate.toDouble
      )
    })

    val ratings = new CoordinateMatrix(parsedData)

    /**      ratings
     * MatrixEntry(2,1,5.0)
     * MatrixEntry(0,0,5.0)
     * MatrixEntry(2,2,1.0)
     * MatrixEntry(0,1,1.0)
     * MatrixEntry(2,3,5.0)
     * MatrixEntry(0,2,5.0)
     * MatrixEntry(2,4,3.0)
     * MatrixEntry(0,3,1.0)
     * MatrixEntry(3,0,1.0)
     * MatrixEntry(0,4,2.0)
     * MatrixEntry(3,1,5.0)
     * MatrixEntry(1,0,5.0)
     * MatrixEntry(3,2,1.0)
     * MatrixEntry(1,1,1.0)
     * MatrixEntry(3,3,5.0)
     * MatrixEntry(1,2,5.0)
     * MatrixEntry(3,4,3.0)
     * MatrixEntry(1,3,1.0)
     * MatrixEntry(2,0,1.0)
     */
    ratings.entries.foreach(e => {
      println(e)
    })

    val matrix = ratings.transpose().toRowMatrix()

    /**            matrix
     * (4,[0,2,3],[2.0,3.0,3.0])
     * (4,[0,1,2,3],[1.0,1.0,5.0,5.0])
     * (4,[0,1,2,3],[5.0,5.0,1.0,1.0])
     * (4,[0,1,2,3],[1.0,1.0,5.0,5.0])
     * (4,[0,1,2,3],[5.0,5.0,1.0,1.0])
     */
    matrix.rows.foreach(r => {
      println(r)
    })

    val similarities = matrix.columnSimilarities(0.1)

    /**         similarities
     * MatrixEntry(1,3,0.3551104121142175)
     * MatrixEntry(0,2,0.4448508420389507)
     * MatrixEntry(2,3,1.0)
     * MatrixEntry(0,1,0.9636241116594316)
     * MatrixEntry(1,2,0.3551104121142175)
     * MatrixEntry(0,3,0.4448508420389507)
     */
    similarities.entries.foreach(e => {
      println(e)
    })

    // user1's average score
    val ratingsOfU1 = ratings.toRowMatrix().rows.collect()(0).toArray
    val avgRatingOfU1 = ratingsOfU1.sum / ratingsOfU1.size

    val ratingsToItem1 = matrix.rows.collect()(0).toArray.drop(1)
    val weights = similarities.entries.filter(_.i == 0).sortBy(_.j).map(_.value).collect()
    val weightedR = (0 to 2).map(t => weights(t) * ratingsToItem1(t)).sum / weights.sum
    println("rating of user 1 towards item 1 is : " + (avgRatingOfU1 + weightedR))
  }
}
