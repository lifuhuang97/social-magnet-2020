#!/bin/sh

javac -d classes -cp src/mysql-connector-java-8.0.19.jar;. src/*.java
java -cp src/mysql-connector-java-8.0.19.jar;classes SocialMagnetApp