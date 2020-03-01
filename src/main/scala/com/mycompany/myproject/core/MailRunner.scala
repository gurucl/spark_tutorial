package com.mycompany.myproject.core

object MailRunner {

  def main():Unit ={

    val mai = new Mail("bda_h2s", "JGAE7Edb")

    mai.sendEmail("hi", "Sample mail from DQ")

    println("SOTUT")

  }
}