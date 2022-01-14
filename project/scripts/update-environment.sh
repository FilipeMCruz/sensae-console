#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

git pull

"$ROOT_DIR"/project/scripts/reset-prod.sh
