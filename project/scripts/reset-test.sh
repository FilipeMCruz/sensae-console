#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

"$ROOT_DIR"/project/scripts/validate-prod-config.sh
if test ! "$?" -eq "0"; then
    echo "Check the documentation at project/README.md to define configurations for all containers"
    exit "$?"
fi

cd "$ROOT_DIR"/project || exit

# seperate container builds to ensure that the host doesn't OOM
docker compose -f docker-compose.test.yml build data-gateway
docker compose -f docker-compose.test.yml build data-validator
docker compose -f docker-compose.test.yml build data-decoder-flow
docker compose -f docker-compose.test.yml build data-processor-flow
docker compose -f docker-compose.test.yml build device-ownership-flow
docker compose -f docker-compose.test.yml build device-management-flow
docker compose -f docker-compose.test.yml build device-commander

docker compose -f docker-compose.test.yml build device-management-frontend
docker compose -f docker-compose.test.yml build data-processor-frontend
docker compose -f docker-compose.test.yml build data-decoder-frontend
docker compose -f docker-compose.test.yml build identity-management-frontend
docker compose -f docker-compose.test.yml build smart-irrigation-frontend
docker compose -f docker-compose.test.yml build fleet-management-frontend
docker compose -f docker-compose.test.yml build rule-management-frontend
docker compose -f docker-compose.test.yml build notification-management-frontend
docker compose -f docker-compose.test.yml build ui-aggregator
docker compose -f docker-compose.test.yml build

docker-compose -f docker-compose.test.yml up -d
