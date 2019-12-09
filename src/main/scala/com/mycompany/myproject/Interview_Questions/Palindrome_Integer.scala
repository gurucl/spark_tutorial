package com.mycompany.myproject.Interview_Questions

import scala.util.control._

object Palindrome_Integer {

  def main(args: Array[String]): Unit = {

    val list = List(101,535,7350357)

    list.foreach(input => {

      if (isPalindrome(input)) println(s"Number $input is a Palindrome...")

      else println(s"Number $input is not a Palindrome...")

    })

//    isPalindrome(101)
//    isPalindrome(535)
//    isPalindrome(7350357)

    nextPalindrome(1223221)

  }


  def isPalindrome(input: Long): Boolean ={

    var num = input
   // println(s"input = $input")
    var res: Long = 0
    var digit: Long = 0

    while(num>0){

      digit = num%10

      res = (res *10) + digit

      num = num/10

    }

    if (input==res) true

    else false

  }

  def nextPalindrome(input : Int): Unit = {

    val loop = new Breaks;

    loop.breakable {

      for (i <- (input + 1) to Int.MaxValue) {

        var flag = isPalindrome(i)

        //println(s"flag = $flag")

        if (flag == true) {println(s"Number $i is the next Palindrome..."); loop.break();}


      }

    }

  }



}
