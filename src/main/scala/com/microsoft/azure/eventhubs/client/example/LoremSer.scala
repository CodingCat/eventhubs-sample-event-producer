package com.microsoft.azure.eventhubs.client.example

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import com.microsoft.azure.eventhubs.EventData

object LoremSer {

  def main(args: Array[String]): Unit = {
    val eventData1 = new EventData(LoremSender.str_1024.getBytes)
    val eventData2 = new EventData(LoremSender.str_2048.getBytes)
    val eventData3 = new EventData(LoremSender.str_3072.getBytes)
    val eventData4 = new EventData(LoremSender.str_100kb.getBytes)

    for (eventData <- List(eventData1, eventData2, eventData3, eventData4)) {
      val fileOut = new FileOutputStream(s"${eventData.getBodyLength}")
      val out = new ObjectOutputStream(fileOut)
      out.writeObject(eventData)
      out.close()
    }

    for (eventDataLength <- List(eventData1.getBodyLength, eventData2.getBodyLength,
      eventData3.getBodyLength, eventData4.getBodyLength)) {
      val fileIn = new FileInputStream(eventDataLength.toString)
      val objectInputStream = new ObjectInputStream(fileIn)
      val data = objectInputStream.readObject().asInstanceOf[EventData]
      println(new String(data.getBody))
      objectInputStream.close()
      fileIn.close()
    }
  }
}
