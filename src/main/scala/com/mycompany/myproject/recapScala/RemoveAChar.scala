package com.mycompany.myproject.recapScala

object RemoveAChar {

  def main(args: Array[String]): Unit = {

    removeChar("Hello World",'l')
  }

  def removeChar(str:String, ch:Char): Unit ={

    //val newStr =
      str.toList.filter(_!=ch) //.filter(_!=" ")//.map(println(_))

        .foreach(println(_))

    //println(newStr)


  }

}
