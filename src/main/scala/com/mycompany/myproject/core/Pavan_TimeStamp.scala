package com.mycompany.myproject.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Pavan_TimeStamp {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Pavan_TimeStamp_Analysis").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    val cols = List("name","timestamp","value").map(col(_))

    val tsDF = spark.read.option("header","true").csv("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/timestamp.csv")//.select(cols:_*)

    tsDF.printSchema()

    val outDF = tsDF.withColumn("rank",
                              dense_rank().over(Window.partitionBy("name").orderBy(desc("timestamp"))))
                              .filter("rank==1").orderBy("name")

    outDF.show(false)

  }

}

//case class Data(name:String, timestamp: String, value:String)


/*

===== INPUT =======

name,timestamp,value
a,20120112,4
a,20140206,2
b,20110201,6
c,20160203,3
d,20010405,2
d,20190506,2

===== OUTPUT =======

root
 |-- name: string (nullable = true)
 |-- timestamp: string (nullable = true)
 |-- value: string (nullable = true)

+----+---------+-----+----+
|name|timestamp|value|rank|
+----+---------+-----+----+
|a   |20140206 |2    |1   |
|b   |20110201 |6    |1   |
|c   |20160203 |3    |1   |
|d   |20190506 |2    |1   |
+----+---------+-----+----+

 */