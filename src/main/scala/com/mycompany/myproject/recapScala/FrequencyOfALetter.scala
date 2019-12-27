package com.mycompany.myproject.recapScala

import java.util.Scanner

object FrequencyOfALetter {

  def main(args: Array[String]): Unit = {

    val sc = new Scanner(System.in)

    println("Enter a String to caclulate the number of chars:")

    val str = sc.next();

    //"Have a nice Day"

    frequencyCounter(str)

  }


  def frequencyCounter(str:String){

    str.map(x => (x.toLower.toString.trim)).filter(_!="").map(x=>(x,1)).groupBy(_._1).map(x=>(x._1,x._2.map(_._2).sum)).toList.sortBy(-_._2).
    foreach(println)
  }

}



