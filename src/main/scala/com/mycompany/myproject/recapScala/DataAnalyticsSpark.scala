package com.mycompany.myproject.recapScala

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window

object DataAnalyticsSpark {

  def main(args: Array[String]): Unit = {


    @transient lazy val logger = org.apache.log4j.Logger.getLogger(DataAnalyticsSpark.getClass)

    val spark = SparkSession.builder().appName("DataAnalytics_Spark").master(args(0)).getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    logger.info("Reading the First Argument File")

    //println(s"args(0): ${args(1)}")

    val empDS = spark.read.json(args(1))

    val deptDS = spark.read.json(args(2))

    empDS.show(false)

    deptDS.show(false)

    import spark.implicits._

    val joinedDF = empDS.join(deptDS,empDS("id")===deptDS("emp_id")).drop("emp_id").withColumnRenamed("dept_name","dept").as[Resource]


    joinedDF.show(false)

    import org.apache.spark.sql.functions._

    joinedDF.groupBy("dept").agg(count("*").as("members_count"), sum("sal").as("sum_sal"),
      avg("sal").as("avg_sal")
    ).show(false)


    joinedDF.withColumn("rank", rank().over(Window.partitionBy("dept").orderBy(desc("sal")))).show(false)


    joinedDF.withColumn("dense_rank", dense_rank().over(Window.partitionBy("dept").orderBy(desc("sal")))).show(false)

    joinedDF.withColumn("row_number", row_number().over(Window.partitionBy("dept").orderBy(desc("sal")))).show(false)


    joinedDF.withColumn("row_number_all", row_number().over(Window.orderBy(desc("sal")))).show(false)


  }

}


case class Resource (id:BigInt, name:String, sal:Double, dept:String)