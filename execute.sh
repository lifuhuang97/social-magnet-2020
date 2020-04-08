#!/bin/sh

javac -d target/classes -cp lib:src/main/java/project:src/main/java/project/farm:src/main/java/project/exception:src/main/java/project/magnet:src/main/java/project/utilities -sourcepath src/main/java/project/
java -cp lib/mysql-connector-java-8.0.19.jar:target/classes/project project.SocialMagnetApp