package com.mycompany.myproject.core

import java.text.SimpleDateFormat

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Pavan_Hive_Json {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Pavan_Hive_Json_Data_Analysis").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    val tsUDF = udf(convertToHiveTs:(String)=>(String))

    val hiveJsonDF = spark.read.json("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/hive.json")

    hiveJsonDF.printSchema()

    hiveJsonDF.show(false)

    val outDF = hiveJsonDF.withColumn("startTime", tsUDF(col("startTime")))
      .withColumn("endTime", tsUDF(col("endTime")))
      .withColumn("InitialStartTime", tsUDF(col("InitialStartTime")))

    outDF.show(false)


  }

  def convertToHiveTs(input:String):String = {

    val date = new SimpleDateFormat("yyyyMMdd'T'HHmmss").parse(input)

    val output = new SimpleDateFormat("yyyyMMdd HHmmss").format(date)

    //println(output)

    output

  }

}


/*

====== INPUT : hive.json =======
{"endTime": "20190220T084445","GUID": "adhgihdb-jkbdfhwe-knkhdw", "startTime": "20190211T104916", "InitialStartTime": "20181117T011141"}
{"endTime": "20190220T084445","GUID": "adhgihdb-jkbdfhwe-knkhdw", "startTime": "20190211T104916", "InitialStartTime": "20181117T011141"}
{"endTime": "20190220T084445","GUID": "adhgihdb-jkbdfhwe-knkhdw", "startTime": "20190211T104916", "InitialStartTime": "20181117T011141"}

====== OUTPUT =======

root
 |-- GUID: string (nullable = true)
 |-- InitialStartTime: string (nullable = true)
 |-- endTime: string (nullable = true)
 |-- startTime: string (nullable = true)

+------------------------+----------------+---------------+---------------+
|GUID                    |InitialStartTime|endTime        |startTime      |
+------------------------+----------------+---------------+---------------+
|adhgihdb-jkbdfhwe-knkhdw|20181117T011141 |20190220T084445|20190211T104916|
|adhgihdb-jkbdfhwe-knkhdw|20181117T011141 |20190220T084445|20190211T104916|
|adhgihdb-jkbdfhwe-knkhdw|20181117T011141 |20190220T084445|20190211T104916|
+------------------------+----------------+---------------+---------------+

+------------------------+----------------+---------------+---------------+
|GUID                    |InitialStartTime|endTime        |startTime      |
+------------------------+----------------+---------------+---------------+
|adhgihdb-jkbdfhwe-knkhdw|20181117 011141 |20190220 084445|20190211 104916|
|adhgihdb-jkbdfhwe-knkhdw|20181117 011141 |20190220 084445|20190211 104916|
|adhgihdb-jkbdfhwe-knkhdw|20181117 011141 |20190220 084445|20190211 104916|
+------------------------+----------------+---------------+---------------+

 */