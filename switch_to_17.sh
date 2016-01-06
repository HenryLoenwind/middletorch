#!/bin/sh
mv eclipse eclipse_18
mv gradle gradle_18
mv gradlew gradlew_18
mv eclipse_17 eclipse
mv gradle_17 gradle
mv gradlew_17 gradlew
cp switch_to_18.sh switch.sh
./gradlew setupDecompWorkspace
./gradlew eclipse
