#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

./scripts/run-backend-silent-dev.sh alert-dispatcher-backend
./scripts/run-backend-silent-quarkus-dev.sh data-validator
./scripts/run-backend-silent-dev.sh device-commander-backend
./scripts/run-backend-silent-dev.sh device-management-master-backend
./scripts/run-backend-silent-dev.sh device-management-slave-backend
./scripts/run-backend-silent-dev.sh notification-management-backend
./scripts/run-backend-silent-dev.sh rule-management-backend
./scripts/run-backend-silent-dev.sh data-decoder-master-backend
./scripts/run-backend-silent-quarkus-dev.sh data-decoder-flow
./scripts/run-backend-silent-quarkus-dev.sh data-gateway
./scripts/run-backend-silent-dev.sh data-processor-master-backend
./scripts/run-backend-silent-dev.sh data-processor-slave-backend
# ./scripts/run-backend-silent-dev.sh data-store
./scripts/run-backend-silent-dev.sh fleet-management-backend
./scripts/run-backend-silent-dev.sh identity-management-backend
./scripts/run-backend-silent-dev.sh device-ownership-backend
./scripts/run-backend-silent-dev.sh smart-irrigation-backend
./scripts/run-backend-silent-dev.sh notification-dispatcher-backend
