#!/usr/bin/sh

checkIfFileExists()
{
    test ! -f "$1" && echo "Missing $1" && valid=1
}


ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR" || exit

valid=0

while read -r line
do
    checkIfFileExists "$line"
done < ./project/scripts/prod_secrets.conf

exit $valid
