package com.microsoft.azure.eventhubs.client.example

import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

class SimpleProducer (val policyName: String,
                       val policyKey: String,
                       val eventHubsNamespace: String,
                       val eventHubsName: String,
                       val eventLength: Int,
                       val partitionId: String) {

  val connectionString: ConnectionStringBuilder = new ConnectionStringBuilder(eventHubsNamespace, eventHubsName,
    policyName, policyKey)
  println(s"CONNECTION STRING: $connectionString")
  val eventHubsClient: EventHubClient = EventHubClient.createFromConnectionString(connectionString.toString).get

  def run(): Unit = {
    val eventPayLoad = Array.fill(eventLength)('a').mkString
    println(eventPayLoad.getBytes.length)
    val eventData = new EventData(eventPayLoad.getBytes)
    eventHubsClient.sendSync(eventData)
  }
}