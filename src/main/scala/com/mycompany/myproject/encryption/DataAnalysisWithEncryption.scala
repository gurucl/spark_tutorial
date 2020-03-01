package com.mycompany.myproject.encryption

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object DataAnalysisWithEncryption {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("Data_Analysis_With_Encryption").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    import spark.implicits._

    val cols = List("eid","fname","lname","deptId","email","salary")//.map(col(_))

    var empDF = spark.read.option("inferSchema", "true").csv("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/Assignments/employee.csv")

      .toDF(cols: _*) //.as[OGSEmployee]

    empDF.printSchema()

    empDF.show(false)

    val columnsToEncrypt = List("fname","lname","email","deptId")


    val secretKey = "h2sdevops"

    // =============   Encrypting Data in DataFrame =================

    println("=============   Encrypting Data in DataFrame =================")

    val encryptUDF = udf(AES.encrypt(_:String,secretKey))

    for (column <- columnsToEncrypt) {

      empDF = empDF.withColumn(column, encryptUDF(col(column)))

    }

    empDF.show(false)

    // =============   Decrypting Data in DataFrame =================

    println("=============   Decrypting Data in DataFrame =================")

    val decryptUDF = udf(AES.decrypt(_:String,secretKey))

    for (column <- columnsToEncrypt) {

      empDF = empDF.withColumn(column, decryptUDF(col(column)))

    }

    empDF.show(false)

  }


}

case class OGSEmployee(eid:Int, fname:String, lname: String, deptID: String, email: String, salary: Double)


/*

======== INPUT =========

100,Guru,Murthy,501,gurumurthy.cl@optum.com,10000
101,Naresh,Polu,502,naresh.polu@optum.com,20000
102,Shashi,Reddy,501,shashi.reddy123@optum.com,15000
103,Anamika,Singh,503,anamika.singh456@optum.com,20000


======== OUTPUT =========

root
 |-- eid: integer (nullable = true)
 |-- fname: string (nullable = true)
 |-- lname: string (nullable = true)
 |-- deptId: integer (nullable = true)
 |-- email: string (nullable = true)
 |-- salary: integer (nullable = true)

+---+-------+------+------+--------------------------+------+
|eid|fname  |lname |deptId|email                     |salary|
+---+-------+------+------+--------------------------+------+
|100|Guru   |Murthy|501   |gurumurthy.cl@optum.com   |10000 |
|101|Naresh |Polu  |502   |naresh.polu@optum.com     |20000 |
|102|Shashi |Reddy |501   |shashi.reddy123@optum.com |15000 |
|103|Anamika|Singh |503   |anamika.singh456@optum.com|20000 |
+---+-------+------+------+--------------------------+------+

=============   Encrypting Data in DataFrame =================
+---+------------------------+------------------------+------------------------+--------------------------------------------+------+
|eid|fname                   |lname                   |deptId                  |email                                       |salary|
+---+------------------------+------------------------+------------------------+--------------------------------------------+------+
|100|Ska70Oit9YJrm+VibIuBvQ==|ZETUz2pOS2hYrl2JaeUNqA==|CcS0wc2Gm/IWp+cJ/5qmKg==|KZiplps1JxzYPmyUymdqy/qWi2Zw5kbh1oIxrJtVpLE=|10000 |
|101|rtSL7ez5Xc10V6MuRfoLhA==|cJZDQ1dEKwfw5UXfaQP0ew==|x918TWztqly1oFh6P1mr7A==|qKEeribE1245KRXF9rdmKlnEtF6EyKVpFzGp74uYPx0=|20000 |
|102|F26uL0ISEhftSpBPZKXEkA==|isu4dV0W+Yc52ScHkVR0jg==|CcS0wc2Gm/IWp+cJ/5qmKg==|+S5Zuetm1M01/sxYiOTHUi6Sq9VFdeIxBdCtDS0OzpE=|15000 |
|103|zYvkaNHaE5ilvCa7ozUWGQ==|B5ZCgPZ330qQw4Gc7EJ3hw==|8o1ZnL+JWPbfgi3elF9ABg==|w8KUp6amzcYANitdWksCbqtsm2FgPOget2YGbpumP0c=|20000 |
+---+------------------------+------------------------+------------------------+--------------------------------------------+------+

=============   Decrypting Data in DataFrame =================
+---+-------+------+------+--------------------------+------+
|eid|fname  |lname |deptId|email                     |salary|
+---+-------+------+------+--------------------------+------+
|100|Guru   |Murthy|501   |gurumurthy.cl@optum.com   |10000 |
|101|Naresh |Polu  |502   |naresh.polu@optum.com     |20000 |
|102|Shashi |Reddy |501   |shashi.reddy123@optum.com |15000 |
|103|Anamika|Singh |503   |anamika.singh456@optum.com|20000 |
+---+-------+------+------+--------------------------+------+



 */