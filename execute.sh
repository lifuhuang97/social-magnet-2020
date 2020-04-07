#!/bin/sh

javac -d target/classes -cp src/main/java:lib src/main/java/*.java
java -cp lib/mysql-connector-java-8.0.19.jar:target/classes SocialMagnetApp