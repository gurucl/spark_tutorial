package com.mycompany.myproject.Interview_Questions

object FibonacciNumbers {

  def main(args: Array[String]): Unit = {


    println(s"Fibonacci numbers for value 8: ")

    fibonacci(8)

  }

  var a = 0
  var b = 1

  def fibonacci(num:Int): Unit ={

    print(s"$a")

      fibo(num)

  }

  def fibo(num:Int): Unit ={

    var number = num

    if (number < 2){
      return
    }

    else {

      val temp = a+b
      b = a;
      a = temp;

      print(s", $temp")

      number = number-1

      fibo(number)
    }
  }

}
