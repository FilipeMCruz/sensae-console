#!/usr/bin/sh

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

echo "Cleaning old test report"
rm -rf jmeter-tests/report

echo "Shutdown remaning environments"
./script/shutdown.sh

echo "Starting test environment"
./script/reset-test.sh

sleep 60

echo "Running preformance tests"
jmeter -n -t jmeter-tests/test1.jmx -l test-example/jmeter/log.jtl -o jmeter-tests/report

echo "Shutdown test environment"
./script/shutdown.sh
