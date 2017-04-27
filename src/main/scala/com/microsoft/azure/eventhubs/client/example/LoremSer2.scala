package com.microsoft.azure.eventhubs.client.example

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import com.microsoft.azure.eventhubs.EventData

object LoremSer2 {

  def main(args: Array[String]): Unit = {
    val eventData1 = new EventData(Array.fill[Byte](1029)('a'))
    val eventData2 = new EventData(Array.fill[Byte](1029 * 2)('a'))
    val eventData3 = new EventData(Array.fill[Byte](1029 * 3)('a'))
    val eventData4 = new EventData(Array.fill[Byte](1029 * 100)('a'))

    println(eventData1.getBodyOffset)
    println(eventData2.getBodyOffset)
    println(eventData3.getBodyOffset)
    println(eventData4.getBodyOffset)

    println(eventData1.getBodyLength)
    println(eventData2.getBodyLength)
    println(eventData3.getBodyLength)
    println(eventData4.getBodyLength)

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
      println(new String(data.getBody, "ISO-8859-1"))
      objectInputStream.close()
      fileIn.close()
    }
  }
}
