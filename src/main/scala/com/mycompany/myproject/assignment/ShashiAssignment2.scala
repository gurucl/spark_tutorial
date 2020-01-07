package com.mycompany.myproject.assignment

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object ShashiAssignment2 {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder.appName("Processing_CSV").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    val columns = "fname,lname,address,pincode"

    val df = spark.read.csv("file:///Users/gcl/Documents/Github/spark_tutorial/src/main/resources/assignments/data.csv")

      .toDF(columns.split(","):_*)

    df.show(false)

    df.withColumn("fullname", concat($"fname",lit(" "),$"lname"))
      .withColumn("address", split($"address",","))
      .withColumn("addressLine1", ($"address")(0))
      .withColumn("street", ($"address")(1))
      .withColumn("pincode", ($"address")(2))
      .drop("fname", "lname", "address").show(false)

  }

}


case class Employee(fname:String, lname:String, address: String, street:String, state:String, pinCode:String)



/*

==========  Input ============

Shashidher,Gunda,"addr1,street,AP",560037
Gurumurthy,C L,"addr2,street,KA",440037

==========  Output ============

+----------+-----+---------------+-------+
|fname     |lname|address        |pincode|
+----------+-----+---------------+-------+
|Shashidher|Gunda|addr1,street,AP|560037 |
|Gurumurthy|C L  |addr2,street,KA|440037 |
+----------+-----+---------------+-------+

+-------+----------------+------------+------+
|pincode|fullname        |addressLine1|street|
+-------+----------------+------------+------+
|AP     |Shashidher Gunda|addr1       |street|
|KA     |Gurumurthy C L  |addr2       |street|
+-------+----------------+------------+------+


 */