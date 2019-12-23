package com.mycompany.myproject.recapScala

import scala.io.Source

object DataAnalysis {

  def main(args: Array[String]): Unit = {

    val  bs = Source.fromFile(args(0))

    val lines = bs.getLines().toList

    val salesObj = lines.map(line =>{

      val obj = Employee.parse(line)

      if (obj.isRight)  (true, obj.right.get)

      else (false, line)
    })


    val goodRecords = salesObj.filter(_._1==true).map(_._2.asInstanceOf[Employee])

    val badRecords = salesObj.filter(_._1==false).map(_._2)

    println("Good Records:\n")

    goodRecords.foreach(println(_))


    println("\nBad Records:\n")

    badRecords.foreach(println(_))


    val allSal = goodRecords.map(_.sal)

    val totSal = allSal.sum

    val avgSal = totSal/allSal.length

    println(s"\nTotal Average Salary: $avgSal\n")


    goodRecords.map(emp => (emp.dept, emp.sal)).groupBy(x=>(x._1)).map(x => (x._1, x._2.size, x._2.map(_._2).sum, x._2.map(_._2).sum/(x._2.size)))


        .map{case (x,y,z,a)=>( println(s"Department $x has $y members with Total Salary: $z and Average  Salary: $a"))}

        //.foreach(println(_))



    bs.close()

  }

}

case class Employee(id:Int, fname:String, lname:String, sal:Double, dept:String)

object Employee {


  def parse(line:String): Either[MalFormedException, Employee] ={

    val fields = line.split(Constants.COMMA)

    if (fields.length!=5){

      Left(new MalFormedException())
    } else {

      Right(Employee(fields(0).toInt, fields(1), fields(2), fields(3).toDouble, fields(4) ))
    }

  }

}


