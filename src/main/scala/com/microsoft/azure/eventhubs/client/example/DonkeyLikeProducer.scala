package com.microsoft.azure.eventhubs.client.example

object DonkeyLikeProducer {


  def main(args: Array[String]): Unit = {
    val policyName = args(0)
    val policyKey = args(1)
    val eventHubNamespace = args(2)
    val eventHubName = args(3)
    val partition = args(4)
    val eventLength = args(5).toInt

    val producer = new SimpleProducer(policyName, policyKey, eventHubNamespace, eventHubName,
      eventLength, "0")
    producer.run()
  }
}
