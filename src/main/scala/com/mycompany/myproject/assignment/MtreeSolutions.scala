package com.mycompany.myproject.assignment

import com.mycompany.myproject.utils.SessionBuilder
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object MtreeSolutions extends Serializable {

  def main(args: Array[String]): Unit = {


    val spark = SessionBuilder.getSessionBuilder("CG_Int_Questions","local[1]")

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._


    val empCols = List("eid","fName","lName","deptId","sal")
    val deptCols = List("deptId","deptName")

    val empDF = spark.read.csv("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/emp.csv").toDF(empCols:_*)

    val deptDF = spark.read.csv("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/dept.csv").toDF(deptCols:_*)

    val employeeDf = empDF.withColumn("FullName", concat($"fName",$"lName") )

    empDF.printSchema()
    empDF.show(false)

    deptDF.printSchema()
    deptDF.show(false)

    val joinedDF = employeeDf.join(deptDF,"deptID").drop("fName","lName")

    joinedDF.show(false)

    val salRankDF = joinedDF.withColumn("salDenserank", dense_rank().over(Window.orderBy($"sal".desc)))

    val salDeptRankDF = joinedDF.withColumn("salDenserank", dense_rank().over(Window.partitionBy($"deptName").orderBy($"sal".desc)))

    salRankDF.show(false)

    salDeptRankDF.show(false)


    joinedDF.withColumn("rank", rank().over(Window.orderBy($"sal".desc))).filter("rank=1")
      //orderBy($"sal".desc).select("fullName","sal")
    .show(false)



  }

}

case class OGSEmployee(eid:Int, fname:String, lname:String, deptId:Int, salary:Double)

case class Dept(deptId:Int, deptName:String)
