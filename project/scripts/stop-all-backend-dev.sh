#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

kill $(cat ./test-examples/dev-run/pid/alert-dispatcher-backend) && rm ./test-examples/dev-run/pid/alert-dispatcher-backend
kill $(cat ./test-examples/dev-run/pid/data-validator-backend) && rm ./test-examples/dev-run/pid/data-validator-backend
kill $(cat ./test-examples/dev-run/pid/device-commander-backend) && rm ./test-examples/dev-run/pid/device-commander-backend
kill $(cat ./test-examples/dev-run/pid/device-management-master-backend) && rm ./test-examples/dev-run/pid/device-management-master-backend
kill $(cat ./test-examples/dev-run/pid/device-management-slave-backend) && rm ./test-examples/dev-run/pid/device-management-slave-backend
kill $(cat ./test-examples/dev-run/pid/notification-management-backend) && rm ./test-examples/dev-run/pid/notification-management-backend
kill $(cat ./test-examples/dev-run/pid/rule-management-backend) && rm ./test-examples/dev-run/pid/rule-management-backend
kill $(cat ./test-examples/dev-run/pid/data-decoder-master-backend) && rm ./test-examples/dev-run/pid/data-decoder-master-backend
kill $(cat ./test-examples/dev-run/pid/data-decoder-slave-backend) && rm ./test-examples/dev-run/pid/data-decoder-slave-backend
kill $(cat ./test-examples/dev-run/pid/data-gateway) && rm ./test-examples/dev-run/pid/data-gateway
kill $(cat ./test-examples/dev-run/pid/data-processor-master-backend) && rm ./test-examples/dev-run/pid/data-processor-master-backend
kill $(cat ./test-examples/dev-run/pid/data-processor-slave-backend) && rm ./test-examples/dev-run/pid/data-processor-slave-backend
# kill $(cat ./test-examples/dev-run/pid/sharespot-data-store) && rm ./test-examples/dev-run/pid/sharespot-data-store
kill $(cat ./test-examples/dev-run/pid/sharespot-fleet-management-backend) && rm ./test-examples/dev-run/pid/sharespot-fleet-management-backend
kill $(cat ./test-examples/dev-run/pid/identity-management-backend) && rm ./test-examples/dev-run/pid/identity-management-backend
kill $(cat ./test-examples/dev-run/pid/device-ownership-backend) && rm ./test-examples/dev-run/pid/device-ownership-backend
kill $(cat ./test-examples/dev-run/pid/smart-irrigation-backend) && rm ./test-examples/dev-run/pid/smart-irrigation-backend
kill $(cat ./test-examples/dev-run/pid/notification-dispatcher-backend) && rm ./test-examples/dev-run/pid/notification-dispatcher-backend
