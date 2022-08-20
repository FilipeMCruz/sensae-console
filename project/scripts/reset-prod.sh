#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

"$ROOT_DIR"/project/scripts/validate-prod-config.sh
if test ! "$?" -eq "0"; then
    echo "Check the documentation at project/README.md to define configurations for all containers"
    exit "$?"
fi

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
docker compose build

docker-compose up -d
