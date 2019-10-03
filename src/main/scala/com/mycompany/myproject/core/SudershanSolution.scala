package com.mycompany.myproject.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SudershanSolution {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Sudershan Solution").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    /**
      * video.txt
      *
      * key|value
      * 100:45 | 200:2#400:6
      * 101:56 | 300:1#310:3
      *
      */

    val inputDF = spark.read.format("csv").option("header", "true").option("delimiter","|")
      .load("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/video.txt")


    inputDF.show(false)

    import spark.implicits._

    val intermediateDF = inputDF.rdd.map(row=>{

      val key = row.getString(0)
      val value = row.getString(1).split("#")

      (Nil:+ key :+ value(0).trim :+ value(1).trim)

    }).toDF("key").withColumn("key",explode(col("key")))


    intermediateDF.show(false)


    val outDF = intermediateDF.select("key").rdd.map( row => {

      val fields = row.getString(0).split(":")

      (fields(0), fields(1))

    }).toDF("video_code","country_code")

    outDF.show(false)





  }

}
