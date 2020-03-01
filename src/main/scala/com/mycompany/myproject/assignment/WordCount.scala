package com.mycompany.myproject.assignment

import com.mycompany.myproject.utils.SessionBuilder
import org.apache.spark.sql.SparkSession

object WordCount {

  def main(args: Array[String]): Unit = {


    val spark = SessionBuilder.getSessionBuilder("WordCount","local[1]")

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val linesRDD = sc.textFile("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/Assignment1.txt")

      .flatMap(_.split("\\s+")).map(_.replaceAll("[-!@#$%{}:,]","")).map((_,1)).reduceByKey(_+_)

      .sortBy(-_._2).take(10).foreach(println)



  }

}
