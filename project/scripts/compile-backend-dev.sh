#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project/backend-services || exit

rm --f -- ../test-examples/backend-comp.log
rm --f -- ../test-examples/bakcend-fail.log

ls | xargs -I % sh -c 'cd % && mvn clean install && echo % >> ../../test-examples/backend-comp.log || echo % >> ../../test-examples/backend-fail.log'
