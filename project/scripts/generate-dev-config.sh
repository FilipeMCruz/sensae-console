#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

. ./secrets/dev.conf

SECRET_BACK=secrets/templates/dev/backend-services
SECRET_FRONT=secrets/templates/dev/frontend-services
SECRET_DB=secrets/templates/dev/databases

BACK_SUFFIX=src/main/resources/application-dev.properties
FRONT_SUFFIX=src/environments/environment.ts

envsubst < $SECRET_BACK/alert-dispatcher-backend.properties > backend-services/alert-dispatcher-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-validator.properties > backend-services/data-validator/$BACK_SUFFIX
envsubst < $SECRET_BACK/device-commander-backend.properties > backend-services/device-commander-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/device-management-master-backend.properties > backend-services/device-management-master-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/device-management-flow.properties > backend-services/device-management-flow/$BACK_SUFFIX
envsubst < $SECRET_BACK/rule-management-backend.properties > backend-services/rule-management-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-decoder-master-backend.properties > backend-services/data-decoder-master-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-decoder-flow.properties > backend-services/data-decoder-flow/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-gateway.properties > backend-services/data-gateway/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-processor-master-backend.properties > backend-services/data-processor-master-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-processor-flow.properties > backend-services/data-processor-flow/$BACK_SUFFIX
envsubst < $SECRET_BACK/data-store.properties > backend-services/data-store/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/fleet-management-backend.properties > backend-services/fleet-management-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/identity-management-backend.properties > backend-services/identity-management-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/device-ownership-backend.properties > backend-services/device-ownership-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/smart-irrigation-backend.properties > backend-services/smart-irrigation-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/notification-management-backend.properties > backend-services/notification-management-backend/infrastructure/boot/$BACK_SUFFIX
envsubst < $SECRET_BACK/notification-dispatcher-backend.properties > backend-services/notification-dispatcher-backend/infrastructure/boot/$BACK_SUFFIX

envsubst < $SECRET_FRONT/device-management-frontend.ts > frontend-services/apps/device-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/rule-management-frontend.ts > frontend-services/apps/rule-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/data-decoder-frontend.ts > frontend-services/apps/data-decoder-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/data-processor-frontend.ts > frontend-services/apps/data-processor-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/fleet-management-frontend.ts > frontend-services/apps/fleet-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/identity-management-frontend.ts > frontend-services/apps/identity-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/smart-irrigation-frontend.ts > frontend-services/apps/smart-irrigation-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/notification-management-frontend.ts > frontend-services/apps/notification-management-frontend/$FRONT_SUFFIX
envsubst < $SECRET_FRONT/ui-aggregator.ts > frontend-services/apps/ui-aggregator/$FRONT_SUFFIX

envsubst < secrets/templates/dev/message-broker/message-broker.env > secrets/dev/message-broker.env

envsubst < $SECRET_DB/init-data-store-database.js > secrets/dev/init-data-store-database.js
envsubst < $SECRET_DB/data-store-database.env > secrets/dev/data-store-database.env
envsubst < $SECRET_DB/data-decoder-database.env > secrets/dev/data-decoder-database.env
envsubst < $SECRET_DB/data-processor-database.env > secrets/dev/data-processor-database.env
envsubst < $SECRET_DB/device-management-database.env > secrets/dev/device-management-database.env
envsubst < $SECRET_DB/identity-management-database.env > secrets/dev/identity-management-database.env
envsubst < $SECRET_DB/notification-management-database.env > secrets/dev/notification-management-database.env
envsubst < $SECRET_DB/rule-management-database.env > secrets/dev/rule-management-database.env
envsubst < $SECRET_DB/smart-irrigation-business-database.env > secrets/dev/smart-irrigation-business-database.env
