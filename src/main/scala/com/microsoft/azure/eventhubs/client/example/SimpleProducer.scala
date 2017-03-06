package com.microsoft.azure.eventhubs.client.example

import scala.collection.mutable.ListBuffer

import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

class SimpleProducer (val policyName: String,
                       val policyKey: String,
                       val eventHubsNamespace: String,
                       val eventHubsName: String,
                       val eventLength: Int,
                       val sendCount: Int) {

  val connectionString: ConnectionStringBuilder = new ConnectionStringBuilder(eventHubsNamespace, eventHubsName,
    policyName, policyKey)
  println(s"CONNECTION STRING: $connectionString")
  val eventHubsClient: EventHubClient = EventHubClient.createFromConnectionString(connectionString.toString).get

  def run(): Unit = {
    import scala.collection.JavaConverters._
    for (i <- 0 until sendCount) {
      val buffer = new ListBuffer[EventData]
      if (i % 1000 == 0) {
        val eventPayLoad = Array.fill(eventLength)('a').mkString
        val eventData = new EventData(eventPayLoad.getBytes)
        buffer += eventData
        eventHubsClient.sendSync(buffer.asJava)
      }
    }
  }
}