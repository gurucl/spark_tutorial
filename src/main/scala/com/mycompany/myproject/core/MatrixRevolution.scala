package com.mycompany.myproject.core

object MatrixRevolution {

  def main(args: Array[String]): Unit = {

    // Calculate the sum of Diagonal elements in a Matrix using Scala

    val multArr = List(List(1,2,3,4),List(5,6,7,8),List(9,10,11,12),List(13,14,15,16))

    val multArrTuple = List((1,2,3,4),(5,6,7,8),(9,10,11,12),(13,14,15,16))

    multArr.foreach(println)

    var res=0
    var j=0

    for(i<-multArr){

      println(s"Element: ${i(j)}")

      res=res+i(j)

      j=j+1
    }


    println(s"\nResult : $res")


  }

}
