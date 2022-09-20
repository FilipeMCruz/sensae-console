#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

# seperate container builds to ensure that the host doesn't OOM
docker compose -f docker-compose.test.yml build data-gateway
docker compose -f docker-compose.test.yml build data-validator
docker compose -f docker-compose.test.yml build data-decoder-flow
docker compose -f docker-compose.test.yml build data-processor-flow
docker compose -f docker-compose.test.yml build device-ownership-flow
docker compose -f docker-compose.test.yml build device-management-flow
docker compose -f docker-compose.test.yml build device-commander
docker compose -f docker-compose.test.yml build data-store

docker compose -f docker-compose.test.yml build smart-irrigation-backend 
docker compose -f docker-compose.test.yml build fleet-management-backend 
docker compose -f docker-compose.test.yml build notification-management-backend
docker compose -f docker-compose.test.yml build notification-dispatcher-backend
docker compose -f docker-compose.test.yml build alert-dispatcher-backend
docker compose -f docker-compose.test.yml build device-management-master-backend
docker compose -f docker-compose.test.yml build rule-management-backend
docker compose -f docker-compose.test.yml build identity-management-backend
docker compose -f docker-compose.test.yml build data-decoder-master-backend
docker compose -f docker-compose.test.yml build data-processor-master-backend

docker compose -f docker-compose.test.yml build device-management-frontend
docker compose -f docker-compose.test.yml build data-processor-frontend
docker compose -f docker-compose.test.yml build data-decoder-frontend
docker compose -f docker-compose.test.yml build identity-management-frontend
docker compose -f docker-compose.test.yml build smart-irrigation-frontend
docker compose -f docker-compose.test.yml build fleet-management-frontend
docker compose -f docker-compose.test.yml build rule-management-frontend
docker compose -f docker-compose.test.yml build notification-management-frontend
docker compose -f docker-compose.test.yml build ui-aggregator

docker compose -f docker-compose.test.yml build data-decoder-database
docker compose -f docker-compose.test.yml build data-store-database
docker compose -f docker-compose.test.yml build data-processor-database
docker compose -f docker-compose.test.yml build device-management-database
docker compose -f docker-compose.test.yml build identity-management-database
docker compose -f docker-compose.test.yml build rule-management-database
docker compose -f docker-compose.test.yml build notification-management-database
docker compose -f docker-compose.test.yml build fleet-management-data-database
docker compose -f docker-compose.test.yml build smart-irrigation-data-database
docker compose -f docker-compose.test.yml build smart-irrigation-business-database

