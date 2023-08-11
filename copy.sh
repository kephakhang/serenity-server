#!/bin/sh

aws s3 cp ./build/libs/test-external-server-1.0.0-all.jar  s3://dev2.twinkorea.io/jar/test-external-server-1.0.0-all.jar
#aws s3 cp ./jks/key.jks s3://dev2.twinkorea.io/jar/key.jks