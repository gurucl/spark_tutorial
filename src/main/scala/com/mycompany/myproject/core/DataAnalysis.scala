package com.mycompany.myproject.core

import org.apache.spark.sql.SparkSession

object DataAnalysis {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("data_Analysis").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val linesRDD = sc.textFile("src/main/resources/emp.csv")

    val salesObj = linesRDD.map(record=>{

      val obj = Employee.parse(record)

      if (obj.isRight) (true, obj.right.get)

      else (false, record)

    })

    val goodRecords = salesObj.filter(_._1 == true).map(_._2.asInstanceOf[Employee])

    println("\nGood Records:")

    goodRecords.foreach(println)

    val badRecords = salesObj.filter(_._1 == false).map(_._2.asInstanceOf[String])

    println("\nBad Records:")

    badRecords.foreach(println)

    val allSal = goodRecords.map(_.sal).collect()

    val totSal = allSal.sum

    println(s"\nTotal Sal: $totSal")

    val avgSal = totSal/allSal.size

    println(s"\nAverage Sal: $avgSal\n")

    goodRecords.map( x=> (x.dept, x.sal)).groupBy(_._1)

      .map(x=>(x._1, x._2.map(_._2).size, x._2.map(_._2).sum, x._2.map(_._2).sum/x._2.map(_._2).size))

      .foreach{ case (dept,count, sum, avg )=> println(s"Department $dept has $count members and Total Salary: $sum" +

        s" and Average Salary: $avg")}

     // .foreach(println)

  }

}

case class Employee(id:Int, first_name:String, last_name:String, sal: Double, dept:String )

object Employee{

  def parse(str: String): Either[MalformedException, Employee] ={

    val fields = str.split(",")

    if (fields.length != 5 ) Left(new MalformedException)

    else  Right(Employee(fields(0).toInt,fields(1),fields(2),fields(3).toDouble,fields(4)))

  }
}

class MalformedException extends Exception { }
