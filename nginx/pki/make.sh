#!/bin/sh


# https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-in-ubuntu-16-04

openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout server.key -out server.crt

openssl dhparam -out dhparam.pem 2048
