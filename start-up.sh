#!/bin/bash
mvn clean package
docker-compose up -d --build
docker ps 
