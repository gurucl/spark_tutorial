package com.mycompany.myproject.assignment.aadhar

import scala.io.Source

object AadharSolutionWithScala {

  var counter=0;

  def main(args: Array[String]): Unit = {

    val bs = Source.fromFile("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/aadhar/auth.csv")
    val lines = bs.getLines().toList

    println(s"Lines Count : ${lines.size}")

    //lines.head.split(",").zipWithIndex.map(x => (s"${x._1}:${x._2}")).foreach(println)

    val records = lines.filter(_!=lines.head).map(_.split(",")).map(row => {

      var aua = getLongValue(row(2))
      val sa = row(3)
      val res_state_name = row(128)

      Data(aua, sa, res_state_name)
    }).filter(_.aua>650000)
      .filter(_.res_state_name.trim != "Delhi")
      .filter(_.sa.matches("[0-9]*")).map(_.sa)

    println(s"Records Count : ${records.size}")

    println(s"Counter value = ${counter}")


    bs.close()
  }

  def getLongValue(str: String):Long ={
    try{
      val out = str.toLong
      out
    }catch {
      case e:Exception => //println(str);counter= counter+1;
        return 650000
    }
  }

}

case class Data(aua:Long, sa:String, res_state_name:String)


