#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

. ./secrets/prod.conf

envsubst < secrets/templates/prod/backend-services/alert-dispatcher-backend.env > secrets/prod/alert-dispatcher-backend.env
envsubst < secrets/templates/prod/backend-services/data-validator-backend.env > secrets/prod/data-validator-backend.env
envsubst < secrets/templates/prod/backend-services/device-commander-backend.env > secrets/prod/device-commander-backend.env
envsubst < secrets/templates/prod/backend-services/device-management-master-backend.env > secrets/prod/device-management-master-backend.env
envsubst < secrets/templates/prod/backend-services/device-management-slave-backend.env > secrets/prod/device-management-slave-backend.env
envsubst < secrets/templates/prod/backend-services/rule-management-backend.env > secrets/prod/rule-management-backend.env
envsubst < secrets/templates/prod/backend-services/data-decoder-master-backend.env > secrets/prod/data-decoder-master-backend.env
envsubst < secrets/templates/prod/backend-services/data-decoder-slave-backend.env > secrets/prod/data-decoder-slave-backend.env
envsubst < secrets/templates/prod/backend-services/data-gateway.env > secrets/prod/data-gateway.env
envsubst < secrets/templates/prod/backend-services/data-processor-master-backend.env > secrets/prod/data-processor-master-backend.env
envsubst < secrets/templates/prod/backend-services/data-processor-slave-backend.env > secrets/prod/data-processor-slave-backend.env
envsubst < secrets/templates/prod/backend-services/data-store-backend.env > secrets/prod/data-store-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-fleet-management-backend.env > secrets/prod/sharespot-fleet-management-backend.env
envsubst < secrets/templates/prod/backend-services/identity-management-backend.env > secrets/prod/identity-management-backend.env
envsubst < secrets/templates/prod/backend-services/device-ownership-backend.env > secrets/prod/device-ownership-backend.env
envsubst < secrets/templates/prod/backend-services/smart-irrigation-backend.env > secrets/prod/smart-irrigation-backend.env
envsubst < secrets/templates/prod/backend-services/notification-management-backend.env > secrets/prod/notification-management-backend.env
envsubst < secrets/templates/prod/backend-services/notification-dispatcher-backend.env > secrets/prod/notification-dispatcher-backend.env
envsubst < secrets/templates/prod/databases/init-data-store-database.js > secrets/prod/init-data-store-database.js
envsubst < secrets/templates/prod/databases/sharespot-common-database.env > secrets/prod/sharespot-common-database.env
envsubst < secrets/templates/prod/databases/data-store-database.env > secrets/prod/data-store-database.env
envsubst < secrets/templates/prod/frontend-services/device-management-frontend.ts > frontend-services/apps/device-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/rule-management-frontend.ts > frontend-services/apps/rule-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-data-decoder-frontend.ts > frontend-services/apps/sharespot-data-decoder-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-data-processor-frontend.ts > frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-fleet-management-frontend.ts > frontend-services/apps/sharespot-fleet-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-identity-management-frontend.ts > frontend-services/apps/sharespot-identity-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/smart-irrigation-frontend.ts > frontend-services/apps/smart-irrigation-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/notification-management-frontend.ts > frontend-services/apps/notification-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/ui-aggregator.ts > frontend-services/apps/ui-aggregator/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/message-broker/message-broker.env > secrets/prod/message-broker.env
