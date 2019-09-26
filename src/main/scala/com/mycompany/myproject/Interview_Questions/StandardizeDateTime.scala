package com.mycompany.myproject.Interview_Questions

import java.text.{ParseException, SimpleDateFormat}
import java.time.{LocalDateTime, ZoneId}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder, DateTimeParseException}
import java.time.temporal.ChronoField
import java.util.Date

import scala.util.control.Breaks

object StandardizeDateTime {

  def main(args:Array[String]): Unit = {

    val list1 = List(
      "2012-12-20 17:35:34.965","",null,
      "2011-13-29 02:35:34","2002-06-31","12312012 040933"
    )

    val outList = list1.foreach(input => {
      val output = dateTimeParser(input, "yyyy-MM-dd'T'HH:mm:ss.SSS")
      println(s"Input: $input  ====> Output : $output")
    })
  }

  def dateTimeParser(inputTS:String,format:String): String ={

    var isoTS: String = null
    val isoSdf = new SimpleDateFormat(format)
    val loop = new Breaks

    val listOfTS = List(
      "yyyy-MM-dd HH:mm:ss.SSSSSSSSS",
      "yyyy-MM-dd HH:mm:ss.SSS",
      "yyyy-MM-dd HH:mm:ss.SSSSSSSS",
      "yyyy-MM-dd HH:mm:ss.SSSSSSS",
      "yyyy-MM-dd HH:mm:ss.SSSSSS",
      "yyyy-MM-dd HH:mm:ss.SSSSS",
      "yyyy-MM-dd HH:mm:ss.SSSS",
      "yyyy-MM-dd HH:mm:ss.SS",
      "yyyy-MM-dd HH:mm:ss.S",
      "yyyy-MM-dd HH:mm:ss",
      "yyyy-M-d HH:mm:ss",
      "yyyyMMdd HHmmss",
      "yyyyMd HHmmss",
      "Mdyyyy HHmmss",
      "MMddyyyy HHmmss",
      "yyyy-M-d HH:mm:ss"
    )

    val listOfDT = List("yyyy-MM-dd","MM-dd-yyyy","yyyy/MM/dd","MM/dd/yyyy","yyyyMMdd",
      "MMddyyyy","yyyyMd","M/d/yyyy","MM/d/yyyy","M/dd/yyyy","yyyy-MM-d","yyyy-M-d")

    var dateValue: String = null
    if(inputTS == null || inputTS.isEmpty || (inputTS!=null && inputTS.trim.isEmpty)) {
      return null
    }
   else {
        loop.breakable {

          if(inputTS.trim.length <= 10){
            for (possibleDTFormats <- listOfDT) {
              val sdf = new SimpleDateFormat(possibleDTFormats)
              sdf.setLenient(false) /// For Strict checking of Date
              try {
                val date = sdf.parse(inputTS.trim)
                isoTS = isoSdf.format(date)
                loop.break()
              } catch {
                case e: ParseException => //println(s" #######  Input Date: $inputTS is not in the format: $possibleTSFormats  ########")
              }
            }
          }

          else {
            for (possibleTSFormats <- listOfTS) {
              val formatter: DateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern(possibleTSFormats)
                .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, false)
                .parseStrict()
                .toFormatter // Nanoseconds = 0-3 digits of fractional second.
              try {
                val localDateTime: LocalDateTime = LocalDateTime.parse(inputTS.trim, formatter)
                val date = Date.from(localDateTime.atZone(ZoneId.systemDefault).toInstant)
                isoSdf.setLenient(false)
                //Convert the parsed date to standard format (sdf = "yyyy-MM-dd HH:mm:ss.SSS")
                isoTS = isoSdf.format(date)
                loop.break()
              } catch {
                case e: DateTimeParseException => //println(s" #######  Input Date: $inputTS is not in the format: $possibleTSFormats  ########")
              }
            }
          }
        }
      }
    isoTS
    }

}
