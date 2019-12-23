package com.mycompany.myproject.recapScala

import org.apache.spark.sql.SparkSession

object WordCountSpark extends Serializable {

  def main(args: Array[String]): Unit = {


    @transient lazy val logger = org.apache.log4j.LogManager.getLogger(WordCountSpark.getClass);

    val spark = SparkSession.builder().appName("WordCount").master(args(0)).getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val linesRDD = sc.textFile(args(1))

    val wordsRDD = linesRDD.flatMap(_.split(Constants.MULTIPLE_WHITE_SPACE)).map(_.trim.toUpperCase().replaceAll("[+,-:;.!@#$%]","")).filter(_!="")

    val countsRDD = wordsRDD.map((_,1)).reduceByKey(_+_).sortBy(-_._2)

    countsRDD.take(10).foreach(println(_))





  }

}
