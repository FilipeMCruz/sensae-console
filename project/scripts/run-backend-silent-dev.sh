#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

java -jar backend-services/$1/infrastructure/boot/target/$1.war > ./test-examples/dev-run/$1.log &

echo $! > ./test-examples/dev-run/pid/$1 &
