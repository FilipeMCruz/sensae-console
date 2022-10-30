#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

# seperate container builds to ensure that the host doesn't OOM
docker compose build data-gateway
docker compose build data-validator
docker compose build data-decoder-flow
docker compose build data-processor-flow
docker compose build device-ownership-flow
docker compose build device-management-flow
docker compose build device-commander

docker compose build device-management-frontend
docker compose build data-processor-frontend
docker compose build data-decoder-frontend
docker compose build identity-management-frontend
docker compose build smart-irrigation-frontend
docker compose build fleet-management-frontend
docker compose build rule-management-frontend
docker compose build notification-management-frontend
docker compose build ui-aggregator

docker compose build smart-irrigation-backend 
docker compose build fleet-management-backend 
docker compose build notification-management-backend
docker compose build notification-dispatcher-backend
docker compose build alert-dispatcher-backend
docker compose build device-management-master-backend
docker compose build rule-management-backend
docker compose build identity-management-backend
docker compose build data-decoder-master-backend
docker compose build data-processor-master-backend

docker compose build data-decoder-database
docker compose build data-store-database
docker compose build data-processor-database
docker compose build device-management-database
docker compose build identity-management-database
docker compose build rule-management-database
docker compose build notification-management-database
docker compose build fleet-management-data-database
docker compose build smart-irrigation-data-database
docker compose build smart-irrigation-business-database
