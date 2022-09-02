#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project/backend-services || exit

docker-compose -f ../docker-compose.dev.yml build

rm --f -- ../reports/backend-test-pass.log
rm --f -- ../reports/backend-test-fail.log

ls -I data-relayer | xargs -I % sh -c 'cd % && mvn test && \
 echo % >> ../../reports/backend-test-pass.log || \
 echo % >> ../../reports/backend-test-fail.log'

test ! -f ../reports/backend-test-fail.log
