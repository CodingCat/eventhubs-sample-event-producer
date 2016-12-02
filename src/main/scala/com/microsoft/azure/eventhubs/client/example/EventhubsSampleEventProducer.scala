/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.azure.eventhubs.client.example

import java.util.Calendar

import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._
//import org.json4s._
//import org.json4s.jackson.Serialization._

import scala.util.Random

//case class EventPayload(payloadBody: String)

class EventhubsSampleEventProducer(
                                    val policyName: String,
                                    val policyKey: String,
                                    val eventHubsNamespace: String,
                                    val eventHubsName: String,
                                    val eventLength: Int,
                                    val eventStartIndex: Long,
                                    val eventCount: Long,
                                    val partitionId: String) {

  def GenerateEvents(): Long = {
    val connectionString: ConnectionStringBuilder = new ConnectionStringBuilder(eventHubsNamespace, eventHubsName,
      policyName, policyKey)
    println(s"CONNECTION STRING: $connectionString")
    val eventHubsClient: EventHubClient = EventHubClient.createFromConnectionString(connectionString.toString).get
    val randomGenerator: Random = new Random()
    var currentEventCount: Long = 0
    var currentEventIndex: Long = eventStartIndex
    val threadId = Thread.currentThread().getId
    val buffer = new java.util.LinkedList[EventData]
    while(true) {
      val currentTime = Calendar.getInstance().getTime
      try {
        val eventPayload = Array.fill(500)('a').mkString
        val eventData = new EventData(eventPayload.getBytes())
        buffer += eventData
        currentEventCount += 1
        if(currentEventCount % 100 == 0) {
          eventHubsClient.sendSync(buffer)
          buffer.clear()
          currentEventIndex = currentEventCount + eventStartIndex
          println(s"$threadId > $currentTime > $currentEventCount > $currentEventIndex > Sending event: $eventPayload")
        }
        if (eventCount > 0 && currentEventCount >= eventCount) return currentEventCount
      } catch {
        case e: Exception => println(s"$currentEventIndex > $currentTime > Exception: $e")
      }
    }
    if (!buffer.isEmpty) {
      eventHubsClient.sendSync(buffer)
    }
    currentEventCount
  }
}