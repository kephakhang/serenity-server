#!/bin/sh

export JAVA_HOME=/home/kepha/.jdks/corretto-17.0.3
export PATH=$JAVA_HOME/bin:$PATH

gradle clean

gradle build --exclude-task test --stacktrace

#export PWD =`pwd`

#zip -r backend-server.zip Dockerfile Dockerrun.aws.json jks/key.jks build/libs/backend-server.jar run.sh stop.sh .ebextensions


