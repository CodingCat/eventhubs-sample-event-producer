package com.microsoft.azure.eventhubs.client.example

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
    for (i <- 0 until sendCount) {
      val eventPayLoad = Array.fill(eventLength)('a').mkString
      val eventData = new EventData(eventPayLoad.getBytes)
      eventHubsClient.sendSync(eventData)
    }
  }
}