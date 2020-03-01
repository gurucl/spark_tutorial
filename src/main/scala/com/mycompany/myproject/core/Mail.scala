package com.mycompany.myproject.core


import java.io.File
import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import javax.activation.DataHandler
import javax.mail.{Address, Message, Session}
import javax.mail.internet.{InternetAddress, MimeBodyPart, MimeMessage, MimeMultipart}
import javax.activation.FileDataSource
//import org.apache.spark.sql.SparkSession
//import org.apache.hadoop.hive.ql.session.SessionState.LogHelper

class Mail(var username: String, var password :String) extends  Serializable {

  @transient lazy val logger = org.apache.log4j.LogManager.getLogger("Mail");
  val host = "mailo2.uhc.com"
  val port = "25"

  val address = "OptumHealthatScale_DevOps_DL@ds.uhc.com"

  def sendEmail(mailSubject: String, mailContent: String) = {
    val properties = new Properties()
    properties.put("mail.smtp.port", port)
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.starttls.enable", "true")

    val session = Session.getDefaultInstance(properties, null)
    val message = new MimeMessage(session)
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
    message.setFrom(new InternetAddress(address));
    message.setText("Thank you!");
    message.setSubject(mailSubject)
    message.setContent(mailContent, "text/html")

    val transport = session.getTransport("smtp")
    transport.connect(host, username, password)
    transport.sendMessage(message, message.getAllRecipients)
  }

  def sendEmail(mailSubject: String, mailContent: String,sendTo : String) = {

    var methodName = "sendEmail"
    val properties = new Properties()
    properties.put("mail.smtp.port", port)
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.starttls.enable", "true")

    val session = Session.getDefaultInstance(properties, null)
    val message = new MimeMessage(session)
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
    message.setSubject(mailSubject)
    message.setContent(mailContent, "text/html")

    val transport = session.getTransport("smtp")
    transport.connect(host, username, password)
    transport.sendMessage(message, message.getAllRecipients)
  }

  def sendEmail(mailSubject: String, mailContent: String,sendTo : String, attachments : List[String]) = {
    val properties = new Properties()
    properties.put("mail.smtp.port", port)
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.starttls.enable", "true")

    val session = Session.getDefaultInstance(properties, null)
    val message = new MimeMessage(session)

    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo).asInstanceOf[Array[Address]])
    message.setSubject(mailSubject)
    val multipart = new MimeMultipart()

    for(filename <- attachments)
    {
      val messageBodyPart = new MimeBodyPart()
      val source = new FileDataSource(filename)
      messageBodyPart.setDataHandler(new DataHandler(source))
      messageBodyPart.setFileName(new File(filename).getName())
      multipart.addBodyPart(messageBodyPart)
    }
    val textBodyPart = new MimeBodyPart();
    textBodyPart.setText(mailContent);
    multipart.addBodyPart(textBodyPart);
    message.setContent(multipart)

    val transport = session.getTransport("smtp")
    transport.connect(host, username, password)
    transport.sendMessage(message, message.getAllRecipients)
  }

}



