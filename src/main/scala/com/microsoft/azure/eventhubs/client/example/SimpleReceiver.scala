package com.microsoft.azure.eventhubs.client.example

import com.microsoft.azure.eventhubs.EventHubClient
import com.microsoft.azure.servicebus.ConnectionStringBuilder

object SimpleReceiver {

  def main(args: Array[String]): Unit = {
    import scala.collection.JavaConverters._
    val policyName = args(0)
    val policyKey = args(1)
    val eventHubNamespace = args(2)
    val eventHubName = args(3)
    val partition = args(4)
    val receiveMaxCount = args(5).toInt

    val connectionString = new ConnectionStringBuilder(eventHubNamespace, eventHubName,
      policyName, policyKey)
    val client  = EventHubClient.createFromConnectionStringSync(connectionString.toString)
    val receiver = client.createReceiverSync("$Default", s"$partition", "-1")
    for (i <- 0 until receiveMaxCount) {
      val eventDatas = receiver.receiveSync(1)
      eventDatas.asScala.foreach(eventData =>
        println(
          s"${eventData.getSystemProperties.getEnqueuedTime.getEpochSecond}," +
            s" ${eventData.getSystemProperties.getOffset}, ${eventData.getSystemProperties.getSequenceNumber}"))
    }

  }
}
