package com.mycompany.myproject.assignment

object Anagram {

  def main(args: Array[String]): Unit = {


    val str1 = "Malayalam"

    val str2 = "malayalam"

    if (checkAnagram(str1,str2)){

      println(s"$str1 and $str2 are anagrams")
    }else {

      println(s"$str1 and $str2 atr not anagrams")

    }


  }


  def checkAnagram(str1:String, str2:String):Boolean={

    var flag = false;

    val temp = str1.toLowerCase().reverse

    if (temp.equalsIgnoreCase(str2)){

      flag = true
    }

    flag
  }

}
