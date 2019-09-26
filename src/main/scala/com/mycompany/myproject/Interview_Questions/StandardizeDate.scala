package com.mycompany.myproject.Interview_Questions

import java.text.{ParseException, SimpleDateFormat}
import java.util

object StandardizeDate {


  def main(args: Array[String]): Unit = {

    val l1 = List("",null,"07-06-1992","195476")

    for(input <- l1){
      val res = dateFormatting(input, "yyyy-MM-dd")
      if (res != null)         println(res)
      else    println("Not a Valid Format")
    }
  }

  def dateFormatting(inputdate: String, expformat: String): String = {

    if(inputdate == null || inputdate.isEmpty || (inputdate!=null && inputdate.trim.isEmpty)) {
     return null
    }
    else {

      try {
        //SDF has the expected Date format
        val sdf = new SimpleDateFormat(expformat)
        sdf.setLenient(false)

        //Get the actual date and parse using the expected Date format
        val dt = sdf.parse(inputdate)

        sdf.format(dt)

      }

      catch {
        case e: ParseException =>
          val dateFormats = new util.ArrayList[SimpleDateFormat]() {
            add(new SimpleDateFormat("yyyy-MM-dd"))
            add(new SimpleDateFormat("M/d/yyyy"))
            add(new SimpleDateFormat("MM/d/yyyy"))
            add(new SimpleDateFormat("M/dd/yyyy"))
            add(new SimpleDateFormat("MM/dd/yyyy"))
            add(new SimpleDateFormat("M-d-yyyy"))
            add(new SimpleDateFormat("MM-d-yyyy"))
            add(new SimpleDateFormat("M-dd-yyyy"))
            add(new SimpleDateFormat("MM-dd-yyyy"))
            add(new SimpleDateFormat("yyyy-M-dd"))
            add(new SimpleDateFormat("yyyy-MM-d"))
            add(new SimpleDateFormat("yyyy-M-d"))
            add(new SimpleDateFormat("yyyy/MM/dd"))
            add(new SimpleDateFormat("yyyy/M/dd"))
            add(new SimpleDateFormat("yyyy/MM/d"))
            add(new SimpleDateFormat("yyyy/M/d"))
            add(new SimpleDateFormat("yyyyMMdd"))
            add(new SimpleDateFormat("yyyyMdd"))
            add(new SimpleDateFormat("yyyyMMd"))
            add(new SimpleDateFormat("yyyyMd"))
            add(new SimpleDateFormat("MMddyyyy"))
            add(new SimpleDateFormat("MMdyyyy"))
          }

          var strDate: String = null
          val invalidDate: String = null
          val sdf = new SimpleDateFormat(expformat)
          sdf.setLenient(false)
          import scala.collection.JavaConversions._
          import scala.util.control._
          val loop = new Breaks

          loop.breakable {
            for (sdf1 <- dateFormats) {
              try {
                sdf1.setLenient(false)
                val date = sdf1.parse(inputdate)
                //Convert the parsed date to standard format (sdf = yyyy-MM-dd)
                strDate = sdf.format(date)
                loop.break()

              } catch {
                case ex: ParseException =>

                //Invalid Format specified
              }
            }
          }

          if (strDate != null) {
            strDate
          }
          else {
            invalidDate

          }
      }
    }
  }
}