#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

docker-compose -f docker-compose.dev.yml down

# docker volume rm docker-sharespot-general-db-dev

docker-compose -f docker-compose.dev.yml up -d
