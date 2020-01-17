package com.mycompany.myproject.assignment

import java.io.FileInputStream
import java.util

import org.apache.spark.sql.expressions.Window
import org.apache.spark.util.collection.CompactBuffer

//import com.mycompany.myproject.assignment.Common_Constants
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source
import org.apache.spark.sql.functions._

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


    val moviesRDD = sc.textFile(moviesFilePath).map(_.split("::")).map(x => Movie(x(0).toInt, x(1), x(2).split("\\|")))
    val moviesDF = moviesRDD.toDF

    //moviesDF.printSchema()
    moviesDF.show(5, false)

    val ratingsRDD = sc.textFile(ratingsFilePath).map(_.split("::")).map(x => Ratings(x(0).toInt, x(1).toInt, x(2).toInt, x(3).toLong))
    val ratingsDF = ratingsRDD.toDF

    //ratingsDF.printSchema()
    ratingsDF.show(5, false)

    val usersRDD = sc.textFile(usersFilePath).map(_.split("::")).map(x => Users(x(0).toInt, x(1), x(2).toInt, x(3).toInt, x(4)))
    val usersDF = usersRDD.toDF()

    //usersDF.printSchema()
    //usersDF.show(5, false)

    ageHashMap = getHashMapFromFile(ageMapFilePath )
    occupationHashMap =getHashMapFromFile(occupationMapFilePath)

    import collection.JavaConverters._

    val ageUDF = udf((key:Int)=>(ageHashMap.asScala.toMap.getOrElse(key,"NA")))
    val occUDF = udf((key:Int)=>(occupationHashMap.asScala.toMap.getOrElse(key,"NA")))


    val getAgrGrp = udf((s:Int) => ageHashMap.asScala.toMap.getOrElse(s,"NA"))
    val getOccupation = udf((s:Int) => occupationHashMap.asScala.toMap.getOrElse(s,"NA"))

    import scala.collection.JavaConverters
    import collection.JavaConverters._

    println("\nPrining Age HashMap values: \n============================ ")
    //ageHashMap.asScala.foreach(println(_))

    println("\nPrining Occupation HashMap values: \n============================ ")
    //occupationHashMap.asScala.foreach(println(_))

   // val ageBroadcastMap = sc.broadcast(ageHashMap)
   // val occBroadcastMap = sc.broadcast(occupationHashMap)


    /* ================  Solving Queries ===================

     1. Top ten most viewed movies with their movies Name (Ascending or Descending order) 

     2. Top twenty rated movies (Condition: The movie should be rated/viewed by at least 40 users)

     3. We wish to know how has the genres ranked by Average Rating, for each profession and age group
        The age groups to be considered are: 18-35, 36-50 and 50+

      */

    // ================ Using Spark RDD =================

    println("\n ====== Most Viewed Movies ======= ")

    val viewsRDD = ratingsRDD.groupBy(_.MovieID).map(x => (x._1, x._2.size))

    val mostViwedRDD = viewsRDD.join(moviesRDD.map(x => (x.MovieID,x.MovieName)))

      .map(x => (x._1, x._2._1, x._2._2)).sortBy(_._2, false,2)
      //.sortBy(-_._2)
    .map(x => (x._3, x._2))//.take(10)//.foreach(println)


    println("\n ====== Most Rated Movies ======= ")

    //val avgRatingRDD = ratingsRDD.groupBy(_.MovieID).map(x => (x._1, x._2.map(_.Rating.toDouble).sum/x._2.map(_.Rating).size ))

    //val mostRatedMoviesRDD = avgRatingRDD.join(viewsRDD).filter(_._2._2 >=40).join(moviesRDD.map(x=>(x.MovieID,x.MovieName)))

      //.sortBy(-_._2._1._1).map(x=>(x._2._2, x._2._1._1))//.take(10)//.foreach(println)


    val ratedRDD = ratingsRDD.groupBy(_.MovieID).map(x=> (x._1, x._2.size, x._2.map(_.Rating.toDouble).sum/x._2.size ))

        .filter(_._2>=40).map(x=>(x._1, x._3)).join(moviesRDD.map(x=> (x.MovieID, x.MovieName))).sortBy(-_._2._1).map(x=>(x._2._2, x._2._1))

       // .take(10).foreach(println)



    //println("\n ====== Genre Rank Movies ======= ")

    val genreRDD = moviesRDD.flatMap(x => (x.Genre.map( g => (x.MovieID, g))))

    //genreRDD.take(10).foreach(println)

    val allRDD = ratingsRDD.map(x=>(x.MovieID,(x.UserID, x.Rating.toDouble))).join(genreRDD).map{ case ((movieId,((userId, rating),(genre)))) => (

      userId,(movieId, rating,genre) )}.join(usersRDD.map(x =>(x.UserID,(x.Occupation, x.Age))))

        .map{ case (userId,((movieId, rating,  genre ),(occ, age)))=>((occ, age, genre),(rating))}

        .groupByKey().map{case ((occ, age, genre),(list))=>((occ, age), genre, (list.sum/list.size))}

        .map{  case (x,y,z) =>{

      val occ = x._1
      val age = x._2
      val genre = y
      val avgRating  = z


      (occupationHashMap.get(occ).toString,ageHashMap.get(age).toString,genre,avgRating)
    }}

    //allRDD.take(10).foreach(println)


    val finalRDD = allRDD.sortBy(-_._4).map{ case (occ,  age, genre, rating)=>((occ,age),genre)}.groupByKey()

      .map(x =>(x._1._1, x._1._2, x._2))

        .map{ case (occ, age, list)=>(

          occ, age, list.toList match {

          case List(a,b,c,d,e,_*) => (a,b,c,d,e)
          case _ => "Not A valid List"
        }
        )}.sortBy(x =>(x._1, x._2))





      //.take(10).foreach(println)














//    val genreRDD = moviesRDD.flatMap(x => {x.Genre.map(genre => ( x.MovieID, genre))})
//
//    val allRDD = ratingsRDD.map(x => (x.MovieID, (x.UserID, x.Rating))).join(genreRDD).map(x=>(x._2._1._1, (x._2._1._2, x._2._2)))
//
//        .join(usersRDD.map(x=>(x.UserID,(x.Occupation, x.Age))))
//
//    val sumRDD = allRDD.map(x=>{
//
//      val occ = x._2._2._1
//      val age = x._2._2._2
//      val genre = x._2._1._2
//      val rating  = x._2._1._1
//
//      ((ageHashMap.get(occ), occupationHashMap.get(age), genre), rating)
//
//    })
//
//    .groupBy(_._1).map(x=> (x._1, x._2.map(_._2.toDouble).sum/ x._2.size)).sortBy(-_._2)
//
//        .map(x=>((x._1._1, x._1._2), x._1._3)).groupBy(_._1)
//
//        .map(x=>(x._1._1, x._1._2, x._2.map(_._2)))
//
//        .map(x => ( x._1, x._2,
//
//          x._3 match {
//
//            case List(a,b,c,d,e,_*) => (a,b,c,d,e)
//
//            case _ =>  "Not a valid List"
//
//          }
//
//        )).sortBy(x=>(x._1,x._2)).filter(_._1=="K-12 student").take(10).foreach(println)
//
//













    // ================ Using Spark DataFrame =================

    println("\n ====== Most Viewed Movies ======= ")

    val viewsDF = ratingsDF.groupBy("MovieID").agg(count("*").as("views"))

    val mostViewsDF = viewsDF .join(moviesDF,"MovieID").orderBy(desc("views"))

      //.show(10, false)


    println("\n ====== Most Rated Movies ======= ")

    val avgRatingDF = ratingsDF.groupBy("MovieID").agg(avg("Rating").as("avgRating"))

    val mostRatedMoviesDF = avgRatingDF.join(viewsDF, "MovieID").filter("views>=40")

      .join(moviesDF, "MovieID").orderBy(desc("avgRating"))

      //.show(10,false)

    println("\n ====== Genre Rank Movies ======= ")

    val genreDF = moviesDF.drop("MovieName").withColumn("Genre", explode($"Genre"))

    val allDF = ratingsDF.drop("Timestamp").join(genreDF, "MovieID")

                .join(usersDF.drop("Gender","Zip_code"), "UserID")

                .groupBy(occUDF($"Occupation").as("Occupation"), ageUDF($"Age").as("Age"), $"Genre").agg(avg("Rating").as("avgRating"))

      .withColumn("Rank",dense_rank().over(Window.partitionBy("Occupation", "Age").orderBy(desc("avgRating"))))

      .groupBy("Occupation","Age").pivot("Rank",List(1,2,3,4,5)).agg(first("Genre"))

      .orderBy("Occupation", "Age")

      .show(false)



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

case class Users(UserID:Int, Gender:String, Age:Int, Occupation:Int, Zip_code: String)

