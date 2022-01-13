#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

docker compose -f docker-compose.yml down
docker compose -f docker-compose.dev.yml down
docker compose -f docker-compose.backend.yml down
docker compose -f docker-compose.local.yml down

