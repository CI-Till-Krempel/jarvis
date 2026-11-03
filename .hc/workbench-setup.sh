#!/bin/bash
set -e
apt-get update
apt-get install -y default-jdk curl unzip
curl -sL https://services.gradle.org/distributions/gradle-8.3-bin.zip -o /tmp/gradle.zip
unzip /tmp/gradle.zip -d /opt/
ln -s /opt/gradle-8.3/bin/gradle /usr/local/bin/gradle
