#!/bin/bash

# https://avro.apache.org/docs/1.11.1/getting-started-java/
# https://mvnrepository.com/artifact/org.apache.avro/avro-tools
# java -jar /path/to/avro-tools-1.11.1.jar compile schema <schema file> <destination>
java -jar avro-tools-1.11.1.jar compile schema src/main/resources/user.avsc  src/main/java
