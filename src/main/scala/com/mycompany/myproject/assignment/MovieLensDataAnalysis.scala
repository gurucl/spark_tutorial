package com.mycompany.myproject.assignment

import java.io.FileInputStream
import java.util

import com.mycompany.myproject.assignment.Common_Constants
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source
import scala.tools.nsc.interpreter.InputStream

object MovieLensDataAnalysis {


  @transient lazy val logger = org.apache.log4j.LogManager.getLogger(MovieLensDataAnalysis.getClass);

  var ageHashMap =  new util.HashMap[Int, String]()
  var occupationHashMap =  new util.HashMap[Int, String]()
  var properties = new util.Properties();

  def main(args: Array[String]): Unit = {

    val startTime:Long = System.currentTimeMillis()

    properties.load(new FileInputStream(args(0)))

    val spark = getSessionBuilder("MovieLens_Data_Analysis", "local[1]")

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    val sparkConf = sc.getConf

    // You can download this data set from this Website => https://grouplens.org/datasets/movielens/


//    val moviesFilePath = sparkConf.get(Common_Constants.SPARK_MOVIES_FILE_PATH)
//    val ratingsFilePath = sparkConf.get(Common_Constants.SPARK_RATINGS_FILE_PATH)
//    val usersFilePath = sparkConf.get(Common_Constants.SPARK_USERS_FILE_PATH)
//    val ageMapFilePath = sparkConf.get(Common_Constants.SPARK_AGE_MAP_FILE_PATH)
//    val occupationMapFilePath = sparkConf.get(Common_Constants.SPARK_OCCUPATION_MAP_FILE_PATH)


    val moviesFilePath = properties.getProperty(Common_Constants.SPARK_MOVIES_FILE_PATH)
    val ratingsFilePath = properties.getProperty(Common_Constants.SPARK_RATINGS_FILE_PATH)
    val usersFilePath = properties.getProperty(Common_Constants.SPARK_USERS_FILE_PATH)
    val ageMapFilePath = properties.getProperty(Common_Constants.SPARK_AGE_MAP_FILE_PATH)
    val occupationMapFilePath = properties.getProperty(Common_Constants.SPARK_OCCUPATION_MAP_FILE_PATH)


    val moviesDF = sc.textFile(usersFilePath).map(_.split("::")).map(x => Movie(x(0).toInt, x(1), x(2).split("\\|"))).toDF

    moviesDF.printSchema()
    moviesDF.show(5, false)

    val ratingsDF = sc.textFile(ratingsFilePath)
      .map(_.split("::")).map(x => Ratings(x(0).toInt, x(1).toInt, x(2).toInt, x(3).toLong)).toDF

    ratingsDF.printSchema()
    ratingsDF.show(5, false)

    val usersDF = sc.textFile(usersFilePath)
      .map(_.split("::")).map(x => Users(x(0).toInt, x(1), x(2).toInt, x(3).toInt, x(4).toLong)).toDF

    usersDF.printSchema()
    usersDF.show(5, false)

    ageHashMap = getHashMapFromFile(ageMapFilePath )
    occupationHashMap =getHashMapFromFile(occupationMapFilePath)

    import scala.collection.JavaConverters
    import collection.JavaConverters._

    println("\nPrining Age HashMap values: \n============================ ")
    ageHashMap.asScala.foreach(println(_))

    println("\nPrining Occupation HashMap values: \n============================ ")
    occupationHashMap.asScala.foreach(println(_))

    val ageBroadcastMap = sc.broadcast(ageHashMap)
    val occBroadcastMap = sc.broadcast(occupationHashMap)






  }

  def getHashMapFromFile(filePath: String ):util.HashMap[Int, String]={

    val map =  new util.HashMap[Int, String]()

    val ageDF = Source.fromFile(filePath).getLines().toList
      .map{ line => {
        val fileds = line.split(":")
        map.put(fileds(0).toInt, fileds(1).replace("\"",""))
        }
      }
    map
  }

  def getSessionBuilder(appName:String, master: String):SparkSession={

    SparkSession.builder()
      .appName(appName)
      .master(master)
      .config("spark.driver.host","localhost")
      .getOrCreate()
  }

  def getDataFrameFromCSV(spark: SparkSession, filePath:String):DataFrame={

    val df = spark.emptyDataFrame

    //    val map = Map("sep"->"::", "inferSchema"-> true)
    //    val ratingsDF = spark.read.format("csv").option("sep","::").option("inferschema",true)
    //    .load("file:////Users/gcl/Downloads/movie_dataset/ratings.dat").as[Ratings]

    df
  }

}

case class Movie(MovieID:Int, MovieName:String, Genre:Array[String])

case class Ratings(UserID:Int, MovieID: Int,Rating: Int, Timestamp:Long)

case class Users(UserID:Int, Gender:String, Age:Int, Occupation:Int, Zip_code: Long)

