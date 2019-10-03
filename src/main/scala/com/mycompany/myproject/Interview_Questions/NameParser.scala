package com.mycompany.myproject.Interview_Questions

object NameParser {

  val m1 = Map("a"-> 1, "b"-> 2, "c"-> 3, "d"-> 4, "e"-> 5, "f"-> 6, "g"->7, "h"->8, "i"-> 9,"j"-> 10, "k"-> 11 , "s" -> 19)

  def main(args: Array[String]): Unit = {


    val str = "Shashi"


    val encodedSum = parser(str)

    println(s"Encoded sum for the string is => $str - $encodedSum")

  }

  def parser(str:String) :Double = {

    var res = 0;

    val l1 = str.map{ key =>

      val value = m1(key.toLower.toString)

      println(key,value)

      res = res+value

    }

    return res
  }

}
