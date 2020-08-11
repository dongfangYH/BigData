package com.example

import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.{SparkConf, SparkContext}

object ALSTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("item_matrix").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val data = sc.textFile("file:///soft/spark/data/mllib/als/test.data")
    val ratings = data.map(_.split(",") match {
      case Array(user, item, rate) => Rating(user.toInt, item.toInt, rate.toDouble)
    })

    val splits = ratings.randomSplit(Array(0.8, 0.2))

    val training = splits(0)
    val test = splits(1)
    val rank = 10
    val numIterations = 10

    /**
     * numBlocks 是用于并行化计算的分块个数 (设置为-1，为自动配置)。
     * rank 是模型中隐语义因子的个数。
     * iterations 是迭代的次数。
     * lambda 是ALS的正则化参数。
     * implicitPrefs 决定了是用显性反馈ALS的版本还是用适用隐性反馈数据集的版本。
     * alpha 是一个针对于隐性反馈 ALS 版本的参数，这个参数决定了偏好行为强度的基准。
     */
    val model = ALS.train(training, rank, numIterations, 0.01)
    val testUsersProducts = test.map{case Rating(user, product, rate) => (user, product)}
    val predictions = model.predict(testUsersProducts).map{case Rating(user, product, rate) => ((user,product),rate)}
    val ratesAndPreds = test.map{case Rating(user, product, rate) => ((user, product), rate)}.join(predictions)
    val MSE = ratesAndPreds.map{ case ((user, product), (r1, r2)) =>
      val  err = (r1 - r2)
      err * err
    }.mean()

  println(MSE)
  }
}
