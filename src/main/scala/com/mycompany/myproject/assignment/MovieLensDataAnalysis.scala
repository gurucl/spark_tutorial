package com.mycompany.myproject.assignment

import org.apache.spark.sql.SparkSession

object MovieLensDataAnalysis {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("MovieLens_DataAnalysis").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    // You can download this data set from this Website => https://grouplens.org/datasets/movielens/

    val moviesDF = sc.textFile("file:///Users/gcl/Downloads/movie_dataset/movies.dat")
                      .map(_.split("::")).map(x => Movie(x(0).toInt, x(1),x(2).split("\\|"))).toDF

    moviesDF.show(5, false)



  }

}

case class Movie(MovieID:Int, MovieName:String, Genre:Array[String])

