#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

"$ROOT_DIR"/project/scripts/validate-prod-config.sh
if test ! "$?" -eq "0"; then
    echo "Check the documentation at project/README.md to define configurations for all containers"
    exit "$?"
fi

cd "$ROOT_DIR"/project || exit

docker compose down

docker compose up -d --build
