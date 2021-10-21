#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

docker-compose down

# docker volume rm docker-sharespot-general-db 

docker-compose up -d --build
