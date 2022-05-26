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
envsubst < secrets/templates/prod/backend-services/sharespot-data-decoder-master-backend.env > secrets/prod/sharespot-data-decoder-master-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-data-decoder-slave-backend.env > secrets/prod/sharespot-data-decoder-slave-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-data-gateway.env > secrets/prod/sharespot-data-gateway.env
envsubst < secrets/templates/prod/backend-services/sharespot-data-processor-master-backend.env > secrets/prod/sharespot-data-processor-master-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-data-processor-slave-backend.env > secrets/prod/sharespot-data-processor-slave-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-data-store-backend.env > secrets/prod/sharespot-data-store-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-fleet-management-backend.env > secrets/prod/sharespot-fleet-management-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-identity-management-backend.env > secrets/prod/sharespot-identity-management-backend.env
envsubst < secrets/templates/prod/backend-services/sharespot-identity-management-slave-backend.env > secrets/prod/sharespot-identity-management-slave-backend.env
envsubst < secrets/templates/prod/backend-services/smart-irrigation-backend.env > secrets/prod/smart-irrigation-backend.env
envsubst < secrets/templates/prod/databases/init-sharespot-data-store-database.js > secrets/prod/init-sharespot-data-store-database.js
envsubst < secrets/templates/prod/databases/sharespot-common-database.env > secrets/prod/sharespot-common-database.env
envsubst < secrets/templates/prod/databases/sharespot-data-store-database.env > secrets/prod/sharespot-data-store-database.env
envsubst < secrets/templates/prod/frontend-services/device-management-frontend.ts > frontend-services/apps/device-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/rule-management-frontend.ts > frontend-services/apps/rule-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-data-decoder-frontend.ts > frontend-services/apps/sharespot-data-decoder-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-data-processor-frontend.ts > frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-fleet-management-frontend.ts > frontend-services/apps/sharespot-fleet-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/sharespot-identity-management-frontend.ts > frontend-services/apps/sharespot-identity-management-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/smart-irrigation-frontend.ts > frontend-services/apps/smart-irrigation-frontend/src/environments/environment.prod.ts
envsubst < secrets/templates/prod/frontend-services/ui-aggregator.ts > frontend-services/apps/ui-aggregator/src/environments/environment.prod.ts
