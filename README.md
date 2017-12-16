# Kafka WebView Examples

This Maven based example project aims to provide a template and easy to use examples for building plug-ins to the [Kafka WebView](#) project.
It is configured with all of the correct dependencies and has a few example implementations to give you a running start
for developing your own Record Filters or Kafka Deserializers to be used with Kafka-WebView.

**Kafka WebView** presents an easy-to-use web based interface for reading data out of kafka topics and providing basic filtering and searching capabilities.

## Writing Custom Filters

The [RecordFilter Interface](https://github.com/Crim/kafka-webview/blob/master/kafka-webview-plugin/src/main/java/org/sourcelab/kafkaview/plugin/filter/RecordFilter.java)
is provided by Kafka WebView and is NOT part of the standard Kafka library.  

## Writing Custom Deserializers

The [Deserializer Interface](https://kafka.apache.org/0110/javadoc/org/apache/kafka/common/serialization/Deserializer.html)
is provided by Kafka, WebView requires nothing special or additional above implementing this interface.  If you already 
have a Deserializer implementation for consuming from Kafka then you simply can just use it as is.

If you don't already have an implementation, you can view the [interface here](https://github.com/apache/kafka/blob/0.11.0/clients/src/main/java/org/apache/kafka/common/serialization/Deserializer.java).

### Packaging a Jar

It should be as simple as issuing the command `mvn package` and retrieving the compiled JAR from the target/ directory.
               
If you're building from your own project, you'll need to package a JAR that contains your implementation along with
any of it's required dependencies, excluding the Kafka or Kafka-WebView-Plugin dependencies.

