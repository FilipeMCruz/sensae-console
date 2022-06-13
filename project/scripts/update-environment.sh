#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

git pull

"$ROOT_DIR"/project/scripts/generate-dev-config.sh
"$ROOT_DIR"/project/scripts/generate-prod-config.sh

"$ROOT_DIR"/project/scripts/reset-prod.sh
