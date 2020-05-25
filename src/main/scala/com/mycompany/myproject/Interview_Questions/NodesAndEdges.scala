package com.mycompany.myproject.Interview_Questions


import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

object NodesAndEdges {

  def main(args: Array[String]): Unit = {

    //val log = Logger.getLogger(getClass.getName)

    val spark = SparkSession.builder().appName("Node and Edges Solving").master("local[1]").getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    //log.info(s"Java Version ${System.getProperty("java.version")}")
    // log.info(s"Spark Version ${spark.version}")

    println(s"==== Java Version ${System.getProperty("java.version") } ==== ")

    println(s"==== Spark Version ${spark.version} =====")

    // ToAndFromCollection

    val nodesAndEdgesRdd = spark.sparkContext.textFile("/Users/gcl/Downloads/edges.csv") // args(0)

    val head = nodesAndEdgesRdd.first()

    println(s"==== Skipping Header = ${head}")

    val degreeOfVertices = nodesAndEdgesRdd.filter( input => input != head) .flatMap(_.split(","))

      .map(_.trim).map((_,1)).reduceByKey(_+_)

    println(s"==== DegreeOfVertices ======")

    degreeOfVertices.foreach(println)

    val averageOfDegree = degreeOfVertices.map(_._2).sum()/(degreeOfVertices.count())

    println(s" AverageOfDegree is : $averageOfDegree ")

    val orderOfVerticesOnDegree = degreeOfVertices.sortBy(_._1).sortBy(-_._2)

    println(s"===== orderOfVerticesOnDegree ======")
    orderOfVerticesOnDegree.foreach(println)

    val EdgesSatisfiesAverageDegree = degreeOfVertices.filter(_._2>= averageOfDegree)

    println(s"===== EdgesSatisfiesAverageDegree ======")
    EdgesSatisfiesAverageDegree.sortBy(-_._2).foreach(println)

    spark.stop()

  }

}

