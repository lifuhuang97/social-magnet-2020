#!/bin/sh

javac -d target/classes -cp src/main/java:lib src/main/java/*.java
java -cp target/classes:lib SocialMagnetApp