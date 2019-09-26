package com.mycompany.myproject.core

import org.apache.spark.sql.SparkSession

object WordCount {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Word_Count").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val linesRDD = sc.textFile("src/main/resources/spark_text.txt")

    val wordsRDD = linesRDD.flatMap(_.split("\\s+")).map(x=>(x.trim.replaceAll("[./!@~#$%^&-+]","").filter(_!="").toUpperCase(),1))

    val countRDD = wordsRDD.reduceByKey(_+_).sortBy(-_._2).take(10)

    countRDD.foreach(println)


  }

}
