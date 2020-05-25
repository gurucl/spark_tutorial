package com.mycompany.myproject.core

import org.apache.spark.sql.{DataFrame, SparkSession}

object BravoSolution {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Bravos").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val confDF = spark.read.option("header","true").option("inferSchema","true")
      //.option("delimeter","$")
      .csv("src/main/resources/Assignments/bravo_conf.csv")

    //val colNames = getColumnValue(confDF,"tablename:package","columns")


    confDF.foreach(row =>{
      val tableName = row.getAs[String]("tableName")
      val colmnNamesWithIndex = row.getAs[String]("columns")
        .split(",").zipWithIndex

      for((colName,index) <- colmnNamesWithIndex ){
        println(s"index: ${index+1}  & colName : $colName" )


      }
    })


    val colNames = confDF.filter("tablename=='package'")
      .select("columns").first().getString(0)

    val colNamesWithIndex = colNames.split(",").zipWithIndex

    colNamesWithIndex.foreach(println)

    for((colName,index) <- colNamesWithIndex){

      println(s"index: ${index+1}  & colName : $colName" )

    }


  }


  def getColumnValue(df:DataFrame, filtercol:String, outCol: String ): String ={

    val fields = filtercol.split(":")
    val colName = fields(0)//.toString
    val colValue = fields(1)//.toString

    df.filter(s"$colName=='$colValue'").select(outCol).first().getString(0)


  }

}
