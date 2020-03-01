package com.mycompany.myproject.assignment

import com.mycompany.myproject.utils.SessionBuilder

object UniversityCounts {

  def main(args: Array[String]): Unit = {

    val spark = SessionBuilder.getSessionBuilder("University_Count","local[1]")

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val linesRDD = sc.textFile("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/university.csv")

    val wordsOnFirstHalf = linesRDD.map(_.split(",")).map(x => x(0)).flatMap(_.split("\\s+"))

      .map(words => words.toUpperCase().replaceAll("[!@#%&*-,.]","")).map(x => (x,1))

      .reduceByKey(_+_).sortBy(-_._2).take(10)

      .foreach(println(_))


  }

}
