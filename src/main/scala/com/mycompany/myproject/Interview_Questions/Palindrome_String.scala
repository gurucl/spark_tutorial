package com.mycompany.myproject.Interview_Questions

object Palindrome_String {

  def main(args: Array[String]): Unit = {

    isPalindrome("Malayalam")

    isPalindrome("Sushi")

  }

  def isPalindrome(input: String): Unit ={

    val str = input.trim.toLowerCase()

    if (str == str.reverse) println(s"Given String $input is a Palindrome...")

    else println(s"Given String $input is not a Palindrome...")

  }

}
