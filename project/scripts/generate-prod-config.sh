#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

. ./secrets/prod.conf

SECRET_BACK=secrets/templates/prod/backend-services
SECRET_FRONT=secrets/templates/prod/frontend-services
SECRET_DB=secrets/templates/prod/databases

BACK_PREFFIX=secrets/prod
FRONT_PREFFIX=frontend-services/apps
FRONT_SUFFIX=src/environments/environment.prod.ts

envsubst < $SECRET_BACK/alert-dispatcher-backend.env > $BACK_PREFFIX/alert-dispatcher-backend.env
envsubst < $SECRET_BACK/data-validator.env > $BACK_PREFFIX/data-validator.env
envsubst < $SECRET_BACK/device-commander-backend.env > $BACK_PREFFIX/device-commander-backend.env
envsubst < $SECRET_BACK/device-management-master-backend.env > $BACK_PREFFIX/device-management-master-backend.env
envsubst < $SECRET_BACK/device-management-slave-backend.env > $BACK_PREFFIX/device-management-slave-backend.env
envsubst < $SECRET_BACK/rule-management-backend.env > $BACK_PREFFIX/rule-management-backend.env
envsubst < $SECRET_BACK/data-decoder-master-backend.env > $BACK_PREFFIX/data-decoder-master-backend.env
envsubst < $SECRET_BACK/data-decoder-slave-backend.env > $BACK_PREFFIX/data-decoder-slave-backend.env
envsubst < $SECRET_BACK/data-gateway.env > $BACK_PREFFIX/data-gateway.env
envsubst < $SECRET_BACK/data-processor-master-backend.env > $BACK_PREFFIX/data-processor-master-backend.env
envsubst < $SECRET_BACK/data-processor-slave-backend.env > $BACK_PREFFIX/data-processor-slave-backend.env
envsubst < $SECRET_BACK/data-store-backend.env > $BACK_PREFFIX/data-store-backend.env
envsubst < $SECRET_BACK/fleet-management-backend.env > $BACK_PREFFIX/fleet-management-backend.env
envsubst < $SECRET_BACK/identity-management-backend.env > $BACK_PREFFIX/identity-management-backend.env
envsubst < $SECRET_BACK/device-ownership-backend.env > $BACK_PREFFIX/device-ownership-backend.env
envsubst < $SECRET_BACK/smart-irrigation-backend.env > $BACK_PREFFIX/smart-irrigation-backend.env
envsubst < $SECRET_BACK/notification-management-backend.env > $BACK_PREFFIX/notification-management-backend.env
envsubst < $SECRET_BACK/notification-dispatcher-backend.env > $BACK_PREFFIX/notification-dispatcher-backend.env

envsubst < $SECRET_FRONT/device-management-frontend.ts > $FRONT_PREFFIX/device-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/rule-management-frontend.ts > $FRONT_PREFFIX/rule-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/data-decoder-frontend.ts > $FRONT_PREFFIX/data-decoder-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/data-processor-frontend.ts > $FRONT_PREFFIX/data-processor-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/fleet-management-frontend.ts > $FRONT_PREFFIX/fleet-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/identity-management-frontend.ts > $FRONT_PREFFIX/identity-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/smart-irrigation-frontend.ts > $FRONT_PREFFIX/smart-irrigation-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/notification-management-frontend.ts > $FRONT_PREFFIX/notification-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/ui-aggregator.ts > $FRONT_PREFFIX/ui-aggregator/$FRONT_SUFFIX

envsubst < secrets/templates/prod/message-broker/message-broker.env > $BACK_PREFFIX/message-broker.env

envsubst < $SECRET_DB/init-data-store-database.js > $BACK_PREFFIX/init-data-store-database.js
envsubst < $SECRET_DB/data-store-database.env > $BACK_PREFFIX/data-store-database.env
envsubst < $SECRET_DB/data-decoder-database.env > $BACK_PREFFIX/data-decoder-database.env
envsubst < $SECRET_DB/data-processor-database.env > $BACK_PREFFIX/data-processor-database.env
envsubst < $SECRET_DB/device-management-database.env > $BACK_PREFFIX/device-management-database.env
envsubst < $SECRET_DB/identity-management-database.env > $BACK_PREFFIX/identity-management-database.env
envsubst < $SECRET_DB/notification-management-database.env > $BACK_PREFFIX/notification-management-database.env
envsubst < $SECRET_DB/rule-management-database.env > $BACK_PREFFIX/rule-management-database.env
envsubst < $SECRET_DB/smart-irrigation-business-database.env > $BACK_PREFFIX/smart-irrigation-business-database.env
