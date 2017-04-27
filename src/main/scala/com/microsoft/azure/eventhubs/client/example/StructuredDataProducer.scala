package com.microsoft.azure.eventhubs.client.example

import java.util.UUID

import scala.collection.mutable.ListBuffer

import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

class StructuredDataProducer(
    val policyName: String,
    val policyKey: String,
    val eventHubsNamespace: String,
    val eventHubsName: String,
    val creationTime: Long,
    val eventLength: Int,
    val sendCount: Int) {

  val connectionString: ConnectionStringBuilder = new ConnectionStringBuilder(eventHubsNamespace, eventHubsName,
    policyName, policyKey)
  println(s"CONNECTION STRING: $connectionString")
  val eventHubsClient: EventHubClient = EventHubClient.createFromConnectionString(connectionString.toString).get

  def run(): Unit = {
    import scala.collection.JavaConverters._
    val buffer = new ListBuffer[EventData]
    for (i <- 0 until sendCount) {
      val eventPayLoad = Array.fill(eventLength)('a').mkString
      val eventData = new EventData(eventPayLoad.getBytes)
      eventData.getProperties.put("creationTime", creationTime.toString)
      eventData.getProperties.put("randomUserProperty", UUID.randomUUID().toString)
      buffer += eventData
      if (i % 100 == 0) {
        eventHubsClient.sendSync(buffer.asJava)
        buffer.clear()
      }
    }
  }
}

object StructuredDataProducer {

  def main(args: Array[String]): Unit = {
    val policyName = args(0)
    val policyKey = args(1)
    val eventHubNamespace = args(2)
    val eventHubName = args(3)
    val sendCount = args(4).toInt
    val eventLength = args(5).toInt

    val creationTime = System.currentTimeMillis()

    val producer = new StructuredDataProducer(policyName, policyKey, eventHubNamespace, eventHubName,
      creationTime, eventLength, sendCount)
    producer.run()
  }
}
