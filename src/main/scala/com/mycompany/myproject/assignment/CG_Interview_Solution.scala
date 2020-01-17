package com.mycompany.myproject.assignment

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

import com.mycompany.myproject.utils._
import org.apache.spark.sql.functions._

import scala.util.Random

object CG_Interview_Solution {

  val from = LocalDate.of(1970,1,1).toString
  val to = LocalDate.of(2020,1,15).toString

  def main(args: Array[String]): Unit = {


    val spark = SessionBuilder.getSessionBuilder("CG_Solutions","local[1]")

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    // 1.	Generate the following data frame:

    val colourDF = spark.read.option("header","true").option("inferschema","true")

                    .csv("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/cg.csv")


    colourDF.printSchema()

    colourDF.show(false)

    // 2.	Remove the space in the string for ‘Description THREE’ and ‘Description Four’. Also, capitalize the letters in ‘Four’ for ‘Description Four’.


    import spark.implicits._

    val clearSpaceUDF = udf((str:String)=>(str.replaceAll("\\s+","")))

    val upperCaseUDF = udf((str:String)=>(str.toUpperCase))

    val combineUDF = udf(combineFunc:String=>String)

    val updatedDF = colourDF.withColumn("Description",combineUDF($"Description").as("Description"))

    //updatedDF.show(false)

    // 3.	Change all of the numbers in ‘Input 3’ to show four decimal places.

    val decimalUDF = udf(decimalFormat: Double => String)

    val decimalDF = updatedDF.withColumn("Input3",decimalUDF($"Input3"))

    decimalDF.show(false)

    // 4.	Generate another data frame as follows:
    // 5. Add column headers to this data frame where column 1 is called ‘Input 1’ and column 2 is called ‘Day Period’.

    val timeDF = spark.read.option("inferschema","true")

      .csv("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/time.csv").toDF("Input1","DayPeriod")

    timeDF.show(false)

    // 6. Left join the table from 4 to the table in 1.

    val joinedDF = decimalDF.join(timeDF,"Input1")

    joinedDF.show(false)

    // 7. Add a column to 6 that generates random dates in ‘YYYYMMDD’ format. Call this column ‘Date’.



    val randomDateUDF = udf(randomDate:(String,String)=>String)

    val randomDateDF = joinedDF.withColumn("Date", randomDateUDF(lit(from), lit(to)) )

    randomDateDF.show(false)

    // 8. Filter from this table all values that are less than ‘1.31’ in ‘Input 3’ and not equal to ‘Red’ or ‘Green’ in ‘Input 1’.

    val toDouble = udf(filterDouble:String => Double)

    val filteredDF = randomDateDF

      .filter((toDouble($"Input3")<1.31) && ($"Input1"!="Red") || ($"Input1")!="Green")

    // filteredDF.printSchema()

    filteredDF.show(false)

    // 9. Create a flag that identifies all records that are greater than the middle date amongst the dates
    //
    //      generated and that are also greater than 1 in ‘Input 2’.

    val dateUDF = udf(getMiddleDate:(Date, Date, Date)=>(Boolean))

    val getFlagUDF = udf(getFlag:(Date, Int)=>(Boolean))

    filteredDF.cache()

    val flagDF = filteredDF.withColumn("dateFlag" , getFlagUDF(to_date($"Date", "yyyy-MM-dd"), $"Input2") )

    flagDF.printSchema()

    flagDF.show(false)


    // 10. Create a for loop that will sum all values in ‘Input 3’ by ‘Description’ and divide by
    //
    //          the minimum value in ‘Input 2’, then put them in separate data frames.



    val groupedDF = filteredDF.groupBy("Description").agg(sum("Input3").as("Description_Input3_agg"))


    groupedDF.show(false)

    // 11. Create a function that will carry out 5-10. Add exception handling.



  }

  def combineFunc(str: String): String = {

    val fields = str.trim.split("\\s+")

    if (fields.length==2){

      val field1 = fields(0)
      val field2 = fields(1).toUpperCase()

      field1 + field2

    }else {

      str.trim
    }
  }

  def decimalFormat(number: Double):String={

    //println(s"number: $number")
    val str = (f"$number%1.4f")
    //println(s"str: $str")
      str//.toDouble
  }

  def randomDate(fromDate: String, toDate: String): String ={

    import java.time.temporal.ChronoUnit.DAYS

    val startDate = LocalDate.parse( fromDate)

    val diff = DAYS.between(startDate, LocalDate.parse( toDate))

    val random = new Random(System.nanoTime())

    startDate.plusDays(Random.nextInt(diff.toInt)).toString

  }

  def filterDouble(str:String):Double = {

    str.toDouble

  }

  def getMiddleDate(from:Date, to:Date, actual: Date):Boolean={

    val fromMillis = from.getTime
    val toMillis = to.getTime
    val actualMillis = actual.getTime
    val diff = toMillis - fromMillis
    val mid = diff/2

    val midDate = new Date(fromMillis + mid)
    println(midDate)

    return (actualMillis > (fromMillis + mid))



  }

  def getFlag(date:Date, field: Int):Boolean={

    val fromDateMillis = new SimpleDateFormat("yyyy-MM-dd").parse(from).getTime
    val toDateMillis = new SimpleDateFormat("yyyy-MM-dd").parse(to).getTime

    val diff = toDateMillis - fromDateMillis
    val mid = diff/2

    val midDate = new Date(fromDateMillis + mid)

    val flag1 = date.after(midDate)

    println(date + " after "+ (midDate.toString) + ":"+ flag1)

    return ( flag1 && field>1)


  }

  def stageCalc():Unit={


  }


}
