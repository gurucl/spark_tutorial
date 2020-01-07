package com.mycompany.myproject.assignment

import org.apache.spark.sql.SparkSession
import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.sql.functions._

object ShashiAssignment {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .appName("Shashi_Assignment")
      .master("local[1]")
      .config("spark.driver.host","localhost")
      .getOrCreate()

    val sc =spark.sparkContext
    sc.setLogLevel("ERROR")

    val timeDiffUdf = udf(timeDiff:(String,String)=>Long)
    val sortTimeUDF = udf(sortTimes:List[(String,String)]=>Long)

    // ================================== Using RDD  ============================

    val rdd = sc.textFile("file:////Users/gcl/Documents/Github/spark_tutorial/src/main/resources/assignments/shashi.csv")

    rdd.map(_.split(",")).filter(_.length==4).map(x => Job(x(0),x(1),x(2),x(3))).filter(_.JobId!="jobid")

      .groupBy(_.JobName).map(x =>(x._1,sortTimes( x._2.toList.map(t => (t.Status,t.Time)))))

      //.foreach(println(_))


    // ================================== Using DF  ============================

    import spark.implicits._

    val df = spark.read.option("header","true").csv("file:////Users/gcl/Documents/Github/spark_tutorial/src/main/resources/assignments/shashi.csv").as[Job]

    df.show(false)

    val startDF = df.filter("status='STARTING'").withColumnRenamed("time","starttime")  // startDF.show(false)

    val endDF = df.filter("status='ENDED'").withColumnRenamed("time","endtime")    // endDF.show(false)

    val diffDF = startDF.join(endDF,"jobname")  // diffDF.show(false)

    val finalDF = diffDF.withColumn("duration", timeDiffUdf($"starttime",$"endtime")).select("jobname","duration")

    //finalDF.show(false)

    // ================================== Using DF Self Join  ============================


    val selfJoinDF = df.join(df.withColumnRenamed("time","endtime"), "jobname")

    val resultDF = selfJoinDF.withColumn("duration" , timeDiffUdf($"endtime", $"time")).filter("duration>0")

    resultDF.select("jobname","duration").show(false)




    // ================================== Testing timeDiff() UDF ============================
    // println(timeDiff("10:00","15:30"))


  }

  def timeDiff(startTime:String, endTime:String): Long = {

    var startDate:Date = null;
    var endDate:Date = null;
    val sdf = new SimpleDateFormat("hh:mm")
    try {
       startDate = sdf.parse(startTime)  //; println("startTime :" + startDate.getTime) => Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
       endDate = sdf.parse(endTime)      //; println("endTime :" + endDate.getTime)
    }catch {
      case e:java.text.ParseException => println(s"Error has occured while parsing the date: "+ e.getMessage())
        e.printStackTrace()
        return 0;
    }
    // Converting MilliSeconds to Minutes
    (endDate.getTime - startDate.getTime)/(60*1000)
  }

  def sortTimes(list: List[(String, String)]): Long = {

    var startTime = ""
    var endTime = ""
   list.map{ case (status, time) => {

     if (status =="STARTING")  { startTime = time }
     else if(status =="ENDED")  { endTime = time}
   }}
    //println(s"calling timeDiff for : ${startTime} , ${endTime}")
    timeDiff(startTime,endTime)
  }

}

case class Job(JobId: String, JobName:String, Status: String, Time: String)


/*
========== Input Data ================

jobid,jobname,status,time
1,A,STARTING,10:00
1,A,ENDED,10:30
2,B,STARTING,10:30
2,B,ENDED,11:15


========== Output Data ================

(B,45)
(A,30)

*/
