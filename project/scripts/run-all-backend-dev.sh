#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

./scripts/run-backend-silent-dev.sh alert-dispatcher-backend
./scripts/run-backend-silent-dev.sh data-validator-backend
./scripts/run-backend-silent-dev.sh device-commander-backend
./scripts/run-backend-silent-dev.sh device-management-master-backend
./scripts/run-backend-silent-dev.sh device-management-slave-backend
./scripts/run-backend-silent-dev.sh notification-management-backend
./scripts/run-backend-silent-dev.sh rule-management-backend
./scripts/run-backend-silent-dev.sh sharespot-data-decoder-master-backend
./scripts/run-backend-silent-dev.sh sharespot-data-decoder-slave-backend
./scripts/run-backend-silent-dev.sh sharespot-data-gateway
./scripts/run-backend-silent-dev.sh sharespot-data-processor-master-backend
./scripts/run-backend-silent-dev.sh sharespot-data-processor-slave-backend
# ./scripts/run-backend-silent-dev.sh sharespot-data-store
./scripts/run-backend-silent-dev.sh sharespot-fleet-management-backend
./scripts/run-backend-silent-dev.sh sharespot-identity-management-backend
./scripts/run-backend-silent-dev.sh sharespot-identity-management-slave-backend
./scripts/run-backend-silent-dev.sh smart-irrigation-backend