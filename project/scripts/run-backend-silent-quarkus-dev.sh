#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project/backend-services/$1 || exit

./mvnw quarkus:dev > ./test-examples/dev-run/$1.log &

echo $! > ./test-examples/dev-run/pid/$1 &
