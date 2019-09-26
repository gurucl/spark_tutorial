package com.mycompany.myproject.assignment

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Assignment1 {

  def main(args: Array[String]): Unit = {


    val spark = SparkSession.builder().config("spark.driver.host","localhost").
      appName("Json_Data_Analysis").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    //1. Write a program in Spark-core(RDD) to find and print the number of words in the file.

    val linesRDD = sc.textFile("src/main/resources/spark_text.txt")

    val wordsRDD = linesRDD.flatMap(_.split("\\s+")).map(x=>(x.trim.replaceAll("[./!@~#$%^&-+]","").filter(_!="").toUpperCase(),1))

    val countRDD = wordsRDD.reduceByKey(_+_).sortBy(-_._2).take(10)

    //countRDD.foreach(println)


    // 2. Load two files in the form of JSON one with department data and other with employee specific data and join them both using Spark SQL to print the output with three columns emp_id, emp_name, dept_name

    val empDF = spark.read.json("src/main/resources/emp.json")

    val deptDF = spark.read.json("src/main/resources/dept.json")

    //empDF.show(false)

    //deptDF.show(false)

    val joinedDF = empDF.join(deptDF,empDF("id")===deptDF("emp_id"))

    //joinedDF.select("id","name","dept_name").show(false)

   // joinedDF.groupBy("dept_name").agg(count("id").as("Total Number of Members")).show(false)


    // 3.	Given two datasets,
    //  a.	User information (id, email, language, location)
    //  b.	Transaction information (transaction-id, product-id, user-id, purchase-amount, item-description)

    // Use Spark-core (RDD) to find the number of unique locations in which each product has been sold.


    val userRDD = sc.textFile("src/main/resources/user.csv").map(_.split("\\s+"))

    import spark.implicits._
//
    val usersDF = userRDD.map(x=> User(x(0).toInt, x(1), x(2), x(3))).toDF()
//
    usersDF.show(false)

    val transRDD = sc.textFile("src/main/resources/transaction.csv").map(_.split(" "))

    val transDF = transRDD.map(x=> Transaction(x(0).toInt, x(1).toInt, x(2).toInt, x(3).toDouble, x(4))).toDF()
//
    transDF.show(false)
//
    val userTransDF = usersDF.join(transDF, usersDF("id")===transDF("user_id"))
//
    userTransDF.show(false)


    userTransDF.groupBy("product_id").agg(count("location").as("Count")).show(false)


    //val joinedRDD = usersRDD_1.join(transRDD_1)//.map(x=>(x._1,x._2._1,x._2._2))

    //joinedRDD.foreach(println)





  }

}

case class User(id:Int, email:String, language:String, location:String)

case class Transaction(transaction_id:Int, product_id:Int, user_id:Int, purchase_amount:Double, item_description:String)