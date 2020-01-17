package com.mycompany.myproject.utils

import org.apache.spark.sql.SparkSession

object SessionBuilder {

  def getSessionBuilder(appName:String, master: String):SparkSession={

      SparkSession.builder()
        .appName(appName)
        .master(master)
        .config("spark.driver.host","localhost")
        .getOrCreate()

  }

}
