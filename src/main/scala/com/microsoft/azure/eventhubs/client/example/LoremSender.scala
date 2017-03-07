package com.microsoft.azure.eventhubs.client.example

import java.util.Collections

import com.microsoft.azure.eventhubs.{EventData, EventHubClient}
import com.microsoft.azure.servicebus.ConnectionStringBuilder

object LoremSender {

  val str_1024 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent tempus massa quis dolor scelerisque, ac finibus dolor efficitur. Nam id tempus ipsum, sit amet condimentum nisl. Fusce scelerisque volutpat odio quis imperdiet. Sed ex libero, condimentum vel congue vitae, scelerisque ac quam. Nam rhoncus interdum lorem id imperdiet. Cras eget ante nibh. Quisque semper purus quis facilisis auctor. Praesent ut tortor sed turpis hendrerit efficitur. Quisque rhoncus ornare dolor a viverra. Praesent et tortor luctus, commodo arcu malesuada, luctus neque. Sed pharetra neque in lacus euismod, eget venenatis quam lacinia. Nam eget massa rutrum, condimentum eros non, tincidunt magna. Cras at nulla at odio porta rhoncus.\n" +
      "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed blandit nisl vel dui cursus cursus. Praesent quis libero at magna suscipit rhoncus. Fusce nisl est, accumsan ut orci eget, congue vulputate lectus. Fusce tempor ornare laoreet. Etiam tincidunt massa quis id 1024"

  val str_2048 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc at lorem et massa dignissim posuere. Nam sagittis consectetur diam. Phasellus imperdiet enim et justo dignissim dignissim. Cras tincidunt gravida maximus. Proin vitae enim dictum, accumsan ante ut, faucibus est. Sed ex quam, sollicitudin varius ultricies ac, feugiat at ante. In vitae dui ac est porta bibendum. Aliquam non sem eget felis ultricies rutrum eu id justo. Pellentesque id dui massa. Nam ut quam vitae mauris bibendum aliquam eu ut sapien. Nulla mollis placerat velit, sed cursus elit sollicitudin at. Maecenas et ex nunc. Quisque gravida eros id ex maximus, eget tincidunt sem cursus. Nulla eu mi convallis, convallis dui eget, tincidunt erat. Integer malesuada enim vitae est venenatis, ac suscipit nibh rhoncus.\n" +
      "Vivamus dapibus dapibus fringilla. Suspendisse tristique varius elit ut ultrices. Proin sit amet commodo orci. Phasellus id odio vitae libero ultricies viverra sit amet auctor tortor. Nam porttitor, arcu a pharetra egestas, augue tellus laoreet ipsum, vitae mollis turpis metus luctus augue. Vivamus laoreet a est eget eleifend. Curabitur eget eros lectus. Maecenas dolor nibh, feugiat at auctor id, consectetur in ipsum. Suspendisse eget purus eu libero viverra feugiat vitae sit amet purus. Morbi eget risus eu est mattis tristique et ut dui. Donec lacus mi, finibus ut libero sit amet, pulvinar hendrerit est. Mauris eget tempus felis, quis condimentum magna. Nulla hendrerit sem pharetra erat feugiat tincidunt.\n" +
      "Suspendisse porttitor ex sit amet elit pharetra, et volutpat urna imperdiet. Aliquam et dictum risus. Sed nec faucibus tortor. Pellentesque quis nisl maximus, rutrum diam non, molestie enim. Etiam felis mi, ultrices a semper eget, tincidunt in arcu. Morbi a felis id libero molestie consequat vitae nec tellus. Nam sit amet enim ultricies, porttitor ligula ac, eleifend quam. Morbi velit mi, condimentum sit amet erat ut, auctor hendrerit odio. Donec ipsum sem, pellentesque auctor diam a, dictum posuere metus. Donec aliquam elit est cras amet 2048"

  val str_3072 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis finibus vel quam in ornare. Nunc gravida viverra efficitur. Suspendisse efficitur lorem neque, tincidunt convallis ligula semper quis. Donec vitae velit et odio venenatis interdum sed sed ligula. Sed molestie nisl eu nulla lobortis volutpat. Nulla sit amet dolor vulputate, feugiat quam quis, pretium purus. Sed consequat neque ut ornare varius. Morbi porttitor ante lorem, non venenatis libero pharetra sit amet. Praesent sit amet diam vel nibh feugiat aliquam sit amet vel magna. Vivamus id feugiat sapien. Nulla at blandit augue. Maecenas ornare volutpat porta. In hac habitasse platea dictumst. Ut molestie enim at dictum interdum. Quisque nec metus faucibus, congue mi at, imperdiet nunc. Aliquam ullamcorper nisl eu metus mollis, in volutpat quam vehicula.\n" +
      "In hac habitasse platea dictumst. Praesent sed purus auctor, pretium ex a, dictum ipsum. Pellentesque posuere a lectus non porta. Suspendisse in nisi malesuada neque tincidunt semper eget quis risus. Quisque quis leo maximus, facilisis urna ac, interdum arcu. Aenean vitae quam ut nisl posuere imperdiet et sit amet sem. Praesent ac odio gravida ligula finibus euismod in eu nunc. Donec ullamcorper vestibulum ex, ut hendrerit eros laoreet et. Suspendisse vel nisi non orci consectetur accumsan sit amet in sapien. Nam consequat risus sed mauris rhoncus, a tempus enim laoreet.\n" +
      "Nunc tincidunt orci velit, a commodo neque hendrerit quis. Ut et nunc mauris. Donec in vestibulum neque, at sollicitudin sem. Proin facilisis odio dolor, ac iaculis erat sodales vitae. Mauris condimentum posuere magna, vel eleifend augue accumsan sit amet. Nulla lobortis tristique rutrum. Quisque eleifend nunc eget aliquam imperdiet. Proin diam urna, laoreet nec scelerisque a, placerat et nisi. Pellentesque viverra est et volutpat eleifend.\n" +
      "Morbi porttitor enim vitae pretium volutpat. In hac habitasse platea dictumst. Aenean porttitor metus in augue molestie, quis venenatis enim auctor. Aenean cursus arcu est, ut consequat diam dictum a. Vestibulum in nunc libero. Phasellus ornare, velit ut dictum fermentum, mi nunc consectetur ante, id ullamcorper arcu leo et magna. Nulla tempus tortor vitae nisl aliquam pretium.\n" +
      "Mauris mi ipsum, feugiat sed arcu eu, blandit viverra justo. Curabitur fermentum leo sed purus volutpat, eget placerat sapien imperdiet. Quisque blandit lorem diam, vitae convallis felis pulvinar at. Praesent condimentum porttitor nunc. Nulla malesuada nulla ut tellus sollicitudin, vitae vulputate enim pulvinar. Fusce semper, diam at feugiat hendrerit, massa sem interdum urna, at malesuada tortor neque quis turpis. Nunc tincidunt ligula eu dictum venenatis. Donec feugiat urna at dictum ultrices. In vel dui imperdiet, pellentesque turpis non, luctus lacus. Suspendisse hendrerit consequat ante, ac vestibulum odio gravida et.\n" +
      "Praesent imperdiet felis mi, eu placerat purus accumsan non. Nunc semper lobortis purus, et facilisis elit rhoncus eget. Vivamus nibh lacus, sagittis id risus at, porta sagittis libero. Donec vitae est augue volutpat 3072"

  val str_100kb = String.join(" ", Collections.nCopies(35,str_3072)) + " 100kb"



  def main(args: Array[String]): Unit = {
    val policyName = args(0)
    val policyKey = args(1)
    val eventHubNamespace = args(2)
    val eventHubName = args(3)
    val sendCount = args(4).toInt

    val connectionString: ConnectionStringBuilder = new ConnectionStringBuilder(eventHubNamespace, eventHubName,
      policyName, policyKey)
    println(s"CONNECTION STRING: $connectionString")
    val eventHubsClient: EventHubClient = EventHubClient.createFromConnectionString(connectionString.toString).get

    for (i <- 0 until sendCount) {
      eventHubsClient.sendSync(new EventData(str_1024.getBytes()))
      println("sent 1K")
      eventHubsClient.sendSync(new EventData(str_2048.getBytes()))
      println("sent 2K")
      eventHubsClient.sendSync(new EventData(str_3072.getBytes()))
      println("sent 3K")
      eventHubsClient.sendSync(new EventData(str_100kb.getBytes()))
      println("sent 100K")
    }
  }
}
