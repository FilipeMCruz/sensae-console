#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

. ./secrets/dev.conf

envsubst < secrets/templates/dev/backend-services/alert-dispatcher-backend.properties > backend-services/alert-dispatcher-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-validator.properties > backend-services/data-validator/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/device-commander-backend.properties > backend-services/device-commander-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/device-management-master-backend.properties > backend-services/device-management-master-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/device-management-slave-backend.properties > backend-services/device-management-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/rule-management-backend.properties > backend-services/rule-management-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-decoder-master-backend.properties > backend-services/data-decoder-master-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-decoder-slave-backend.properties > backend-services/data-decoder-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-gateway.properties > backend-services/data-gateway/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-processor-master-backend.properties > backend-services/data-processor-master-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-processor-slave-backend.properties > backend-services/data-processor-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/data-store.properties > backend-services/data-store/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/fleet-management-backend.properties > backend-services/fleet-management-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/identity-management-backend.properties > backend-services/identity-management-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/device-ownership-backend.properties > backend-services/device-ownership-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/smart-irrigation-backend.properties > backend-services/smart-irrigation-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/notification-management-backend.properties > backend-services/notification-management-backend/infrastructure/boot/src/main/resources/application-dev.properties
envsubst < secrets/templates/dev/backend-services/notification-dispatcher-backend.properties > backend-services/notification-dispatcher-backend/infrastructure/boot/src/main/resources/application-dev.properties

envsubst < secrets/templates/dev/frontend-services/device-management-frontend.ts > frontend-services/apps/device-management-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/rule-management-frontend.ts > frontend-services/apps/rule-management-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/data-decoder-frontend.ts > frontend-services/apps/data-decoder-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/data-processor-frontend.ts > frontend-services/apps/data-processor-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/fleet-management-frontend.ts > frontend-services/apps/fleet-management-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/identity-management-frontend.ts > frontend-services/apps/identity-management-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/smart-irrigation-frontend.ts > frontend-services/apps/smart-irrigation-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/notification-management-frontend.ts > frontend-services/apps/notification-management-frontend/src/environments/environment.ts
envsubst < secrets/templates/dev/frontend-services/ui-aggregator.ts > frontend-services/apps/ui-aggregator/src/environments/environment.ts

envsubst < secrets/templates/dev/message-broker/message-broker.env > secrets/dev/message-broker.env

envsubst < secrets/templates/dev/databases/init-data-store-database.js > secrets/dev/init-data-store-database.js
envsubst < secrets/templates/dev/databases/data-store-database.env > secrets/dev/data-store-database.env
envsubst < secrets/templates/dev/databases/data-decoder-database.env > secrets/dev/data-decoder-database.env
envsubst < secrets/templates/dev/databases/data-processor-database.env > secrets/dev/data-processor-database.env
envsubst < secrets/templates/dev/databases/device-management-database.env > secrets/dev/device-management-database.env
envsubst < secrets/templates/dev/databases/identity-management-database.env > secrets/dev/identity-management-database.env
envsubst < secrets/templates/dev/databases/notification-management-database.env > secrets/dev/notification-management-database.env
envsubst < secrets/templates/dev/databases/rule-management-database.env > secrets/dev/rule-management-database.env
envsubst < secrets/templates/dev/databases/smart-irrigation-business-database.env > secrets/dev/smart-irrigation-business-database.env
