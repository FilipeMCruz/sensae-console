#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

echo "Cleaning old test report"
rm -rf jmeter-tests/report

. ./jmeter-tests/properties.env

echo "Creating devices according to properties"
./jmeter-tests/scripts/new-dev.sh $SENSAE_DEVICES > jmeter-tests/em300-th/devices.csv

echo "Shutdown remaning environments"
./scripts/shutdown.sh

echo "Starting test environment"
./scripts/reset-test.sh

sleep 60

echo "Running preformance tests"
jmeter -n -t jmeter-tests/test1.jmx -l jmeter-tests/log.jtl -o jmeter-tests/report

echo "Shutdown test environment"
./scripts/shutdown.sh
