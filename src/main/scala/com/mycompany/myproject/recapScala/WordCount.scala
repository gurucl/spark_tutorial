package com.mycompany.myproject.recapScala

import scala.io.Source

object WordCount {

  def main(args: Array[String]): Unit = {

    val bs = Source.fromFile(args(0))

    val lines = bs.getLines().toList

    val words = lines.flatMap(_.split("\\s+")).map(_.trim.toUpperCase().replaceAll("[+.-@%^&*]","")).filter(_!="").filter(_!="-")

    words.map(word=>(word,1)).groupBy(x=> x._1).map(x=>(x._1,x._2.map(_._2).sum)).toList.sortBy(-_._2)

      .take(10).foreach(println)




    bs.close()
  }

}
