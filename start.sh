#!/usr/bin/env bash
mvn clean install
docker build -f app-monitoring.dockerfile -t app-monitor/spring-app-monitoring .
docker-compose up
