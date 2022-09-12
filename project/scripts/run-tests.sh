#!/bin/bash
set -eo pipefail

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

./scripts/generate-test-config.sh "$@"

docker-compose -f docker-compose.build.yml build

rm --f -- reports/backend-test-pass.log
rm --f -- reports/backend-test-fail.log

cd backend-services || exit

ls -I data-relayer | xargs -I % sh -c 'cd % && mvn test && \
 echo % >> ../../reports/backend-test-pass.log || \
 echo % >> ../../reports/backend-test-fail.log'

test ! -f ../reports/backend-test-fail.log

cd ../frontend-services || exit

npm install
npm run test-all

#./../scripts/build-test-images.sh

#docker-compose -f ../docker-compose.test.yml up -d --build

#sleep 180

#npm run e2e-all

#docker-compose -f ../docker-compose.test.yml down
