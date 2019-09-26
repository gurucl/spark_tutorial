package com.mycompany.myproject.Interview_Questions

object Factorial {

  def main(args: Array[String]): Unit = {

    // Write a Factorial Program

    val nums = List(5, 10, 500, 1000)

    nums.foreach(num => {

      val result = fact(num)

      println(s"Factorial of $num = $result")
    })


  }

  // Factorial program using tail-recursion  => Efficient Algorithm to calculate Factorial

  def fact(num: Long, res:BigInt=1): BigInt ={

    if (num<=1) res

    else fact(num-1, res*num)

  }

  // Factorial program without tail-recursion  => Inefficient Algorithm to calculate Factorial

  val res =1

  def fact2(num:Int):BigInt = {

  if(num<=1) return res

  else num * fact(num-1)


  }

}
