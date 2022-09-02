#!/bin/bash
set -eo pipefail

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

sed 's|$SENSAE_ADMIN_EMAIL|test@sensae.pt|g' secrets/templates/dev/databases/identity-management-initdb.sql > \
        databases/identity-management-database/identity-management-initdb.sql

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
npm test-all
