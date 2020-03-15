package com.mycompany.myproject.assignment.aadhar

import scala.io.Source

object AadharSolutionWithScala {

  var counter=0;

  def main(args: Array[String]): Unit = {

    val bs = Source.fromFile("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/aadhar/auth.csv")
    val lines = bs.getLines().toList

    println(s"Total Number of Records in Aadhar Dataset : ${lines.size-1}") // Excluding header

    //lines.head.split(",").zipWithIndex.map(x => (s"${x._1}:${x._2}")).foreach(println)

    val outDF = lines.filter(_!=lines.head).map(_.split(",")).map(row => {

      val aua = getIntValue(row(2))
      val sa = row(3)
      val res_state_name = row(128)

      Data(aua, sa, res_state_name)

    }).filter(_.aua>650000)
      .filter(_.res_state_name != "Delhi")
      .filter(_.sa.matches("[0-9]*"))
       // .map(_.sa).distinct

    println(s"OutDF Records Count : ${outDF.size}")

    println(s"Number of AUA which are not able to convert to Integer value = ${counter}")


    bs.close()
  }

  def getIntValue(str: String):Int = {
    try{
      val out = str.toInt
      out
    }catch {
      case e:Exception => counter= counter+1; // println(str);
        return 650000
    }
  }

}

case class Data(aua:Long, sa:String, res_state_name:String)


/*

Total Number of Records in Aadhar Dataset : 4916
OutDF Records Count : 1736
Number of AUA which are not able to convert to Integer value = 87

 */