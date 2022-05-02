#!/bin/sh


gradle build --exclude-task test --stacktrace

#export PWD =`pwd`

#zip -r backend-server.zip Dockerfile Dockerrun.aws.json jks/key.jks build/libs/backend-server.jar run.sh stop.sh .ebextensions


