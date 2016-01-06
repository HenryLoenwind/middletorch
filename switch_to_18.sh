#!/bin/sh
mv eclipse eclipse_17
mv gradle gradle_17
mv gradlew gradlew_17
mv eclipse_18 eclipse
mv gradle_18 gradle
mv gradlew_18 gradlew
cp switch_to_17.sh switch.sh
./gradlew setupDecompWorkspace
./gradlew eclipse
