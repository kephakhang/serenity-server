#!/bin/sh

docker build -t emoldino/serenity-server:1.0.0 . -f Dockerfile

#docker run --rm -it -p 127.0.0.1:80:8080 kephakhang/backend-server:0.0.7
# http://127.0.0.1:80
#docker run -p 80:8080  --ulimit nofile=65535:65535 kephakhang/backend-server:0.1.0
