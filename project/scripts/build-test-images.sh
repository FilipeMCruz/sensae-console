#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

# seperate container builds to ensure that the host doesn't OOM
docker-compose -f docker-compose.test.yml build data-gateway
docker-compose -f docker-compose.test.yml build data-validator
docker-compose -f docker-compose.test.yml build data-decoder-flow
docker-compose -f docker-compose.test.yml build data-processor-flow
docker-compose -f docker-compose.test.yml build device-ownership-flow
docker-compose -f docker-compose.test.yml build device-management-flow
docker-compose -f docker-compose.test.yml build device-commander

docker-compose -f docker-compose.test.yml build smart-irrigation-backend 
docker-compose -f docker-compose.test.yml build fleet-management-backend 
docker-compose -f docker-compose.test.yml build notification-management-backend
docker-compose -f docker-compose.test.yml build notification-dispatcher-backend
docker-compose -f docker-compose.test.yml build alert-dispatcher-backend
docker-compose -f docker-compose.test.yml build device-management-master-backend
docker-compose -f docker-compose.test.yml build rule-management-backend
docker-compose -f docker-compose.test.yml build identity-management-backend
docker-compose -f docker-compose.test.yml build data-decoder-master-backend
docker-compose -f docker-compose.test.yml build data-processor-master-backend

