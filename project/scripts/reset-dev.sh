#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

"$ROOT_DIR"/project/scripts/build-images.sh

docker compose -f docker-compose.dev.yml down

docker compose -f docker-compose.dev.yml up -d
