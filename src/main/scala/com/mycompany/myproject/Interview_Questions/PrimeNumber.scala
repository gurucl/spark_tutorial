package com.mycompany.myproject.Interview_Questions

import scala.util.control.Breaks

object PrimeNumber {


  def main(args: Array[String]): Unit = {

    val list = List(91, 101,535,7350357)

    list.map(isPrime(_))

  }


  def isPrime(input: Int): Unit = {

    val loop = new Breaks

    var flag = true

    loop.breakable {

      for (i <- 2 to input / 2) {

        if (input % i == 0) {flag = false; loop.break()}

      }

    }

    if (flag==false) println(s"$input is a Prime Number...")

    else println(s"$input is not a Prime Number...")

  }

}
