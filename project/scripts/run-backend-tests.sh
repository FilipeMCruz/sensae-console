#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project/backend-services || exit

rm --f -- ../test-examples/backend-test-pass.log
rm --f -- ../test-examples/backend-test-fail.log

ls | xargs -I % sh -c 'cd % && mvn test && echo % >> ../../test-examples/backend-test-pass.log || echo % >> ../../test-examples/backend-test-fail.log'
