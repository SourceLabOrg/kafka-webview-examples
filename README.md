# Kafka WebView Examples

This Maven based example project aims to provide a template and easy to use examples for building plug-ins to the [Kafka WebView](https://github.com/SourceLabOrg/kafka-webview) project.
It is configured with all of the correct dependencies and has a few example implementations to give you a running start
for developing your own Record Filters or Kafka Deserializers to be used with Kafka WebView.

**Kafka WebView** presents an easy-to-use web based interface for reading data out of kafka topics and providing basic filtering and searching capabilities.

## Getting Started

### Using this Project as a template

The recommended way to get started is by forking or locally cloning this repository and using it as a template, simpling adding your own implementations to it.
If you go this route you can skip the following section as it's already be done for you.

### Setting up your own project

If you'd prefer to setup your own project you'll need to include the following dependencies

```xml
    <!-- Use kafka-webview-plugin dependency -->
    <!-- Scope is provided -->
    <dependency>
        <groupId>org.sourcelab</groupId>
        <artifactId>kafka-webview-plugin</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>

    <!-- Kafka Dependency for Deserializers -->
    <!-- Scope is provided -->
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>0.11.0.1</version>
        <scope>provided</scope>
    </dependency>
```

## Writing Custom Filters

The [RecordFilter Interface](https://github.com/Crim/kafka-webview/blob/master/kafka-webview-plugin/src/main/java/org/sourcelab/kafkaview/plugin/filter/RecordFilter.java)
is provided by Kafka WebView and is NOT part of the standard Kafka library.

The interface looks like:

```java
/**
 * Interface that defines a Record Filter.
 */
public interface RecordFilter {
    /**
     * Define names of configurable options.
     * These names will be passed up to the User Interface and allow the user to define them.
     * When configure() is called below, these same names will be returned, along with the user defined values,
     * in the filterOptions argument.
     *
     * Since the UI provides no validation on these user defined values, best practices dictate that your implementation
     * should gracefully handle when these are not set.
     *
     * @return Set of option names.
     */
    default Set<String> getOptionNames() {
        return new HashSet<>();
    }

    /**
     * Configure this class.
     * @param consumerConfigs Consumer configuration in key/value pairs
     * @param filterOptions User defined filter options.
     */
    void configure(final Map<String, ?> consumerConfigs, final Map<String, String> filterOptions);

    /**
     * Define the filter behavior.
     * A return value of TRUE means the record WILL be shown.
     * A return value of FALSE means the record will NOT be shown.
     *
     * @param topic Name of topic the message came from.
     * @param partition Partition the message came from.
     * @param offset Offset the message came from.
     * @param key Deserialized Key object.
     * @param value Deserialized Value object.
     * @return True means the record WILL be shown.  False means the record will NOT be shown.
     */
    boolean includeRecord(final String topic, final int partition, final long offset, final Object key, final Object value);

    /**
     * Called on closing.
     */
    void close();
}
```  

### Example Implementations

This project includes a few [example implementations](https://github.com/SourceLabOrg/kafka-webview-examples/tree/master/src/main/java/examples/filter) you can use for reference.  

 - [StringSearchFilter.java](#) - Only passes records that contain a configured String value.
 - [LowOffsetFilter.java](#) - Only passes records that have a stored offset greater than a configured value.
 - [ModulusOffsetFilter.java](#) - Only passes specific records based on the offset and a configured modulus value.

## Writing Custom Deserializers

The [Deserializer Interface](https://kafka.apache.org/0110/javadoc/org/apache/kafka/common/serialization/Deserializer.html)
is provided by Kafka, WebView requires nothing special or additional above implementing this interface.  If you already 
have a Deserializer implementation for consuming from Kafka then you simply can just use it as is.

If you don't already have an implementation, you can view the [interface here](https://github.com/apache/kafka/blob/0.11.0/clients/src/main/java/org/apache/kafka/common/serialization/Deserializer.java).

## Packaging a Jar

It should be as simple as issuing the command `mvn package` and retrieving the compiled JAR from the target/ directory.
               
If you're building from your own project, you'll need to package a JAR that contains your implementations along with
any of it's required dependencies, **excluding** the Kafka or Kafka-WebView-Plugin dependencies.

