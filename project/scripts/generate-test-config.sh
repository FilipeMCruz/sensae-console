#!/bin/bash
# needs four arguments:
# 1: MAPBOX_ACCESS_TOKEN
# 2: EXTERNAL_MICROSOFT_AUDIENCE
# 3: EXTERNAL_GOOGLE_AUDIENCE
# 4: SENSAE_ADMIN_EMAIL

ROOT_DIR=$(git rev-parse --show-toplevel)

cd "$ROOT_DIR"/project || exit

. ./secrets/dev.conf

SECRET_FRONT=secrets/test/frontend/
FRONT_SUFFIX=src/environments/environment.ts
PROD_FRONT_SUFFIX=src/environments/environment.prod.ts

cp $SECRET_FRONT/notification-management-frontend.ts frontend-services/apps/notification-management-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/notification-management-frontend.ts frontend-services/apps/notification-management-frontend/$PROD_FRONT_SUFFIX

cp $SECRET_FRONT/identity-management-frontend.ts frontend-services/apps/identity-management-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/identity-management-frontend.ts frontend-services/apps/identity-management-frontend/$PROD_FRONT_SUFFIX

cp $SECRET_FRONT/data-processor-frontend.ts frontend-services/apps/data-processor-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/data-processor-frontend.ts frontend-services/apps/data-processor-frontend/$PROD_FRONT_SUFFIX

cp $SECRET_FRONT/data-decoder-frontend.ts frontend-services/apps/data-decoder-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/data-decoder-frontend.ts frontend-services/apps/data-decoder-frontend/$PROD_FRONT_SUFFIX

cp $SECRET_FRONT/rule-management-frontend.ts frontend-services/apps/rule-management-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/rule-management-frontend.ts frontend-services/apps/rule-management-frontend/$PROD_FRONT_SUFFIX

cp $SECRET_FRONT/device-management-frontend.ts frontend-services/apps/device-management-frontend/$FRONT_SUFFIX
cp $SECRET_FRONT/device-management-frontend.ts frontend-services/apps/device-management-frontend/$PROD_FRONT_SUFFIX


sed "s|\$SENSAE_AUTH_EXTERNAL_MICROSOFT_AUDIENCE|$2|g;s|\$SENSAE_AUTH_EXTERNAL_GOOGLE_AUDIENCE|$3|g" \
    $SECRET_FRONT/ui-aggregator.ts > \
    frontend-services/apps/ui-aggregator/$FRONT_SUFFIX
sed "s|\$SENSAE_AUTH_EXTERNAL_MICROSOFT_AUDIENCE|$2|g;s|\$SENSAE_AUTH_EXTERNAL_GOOGLE_AUDIENCE|$3|g" \
    $SECRET_FRONT/ui-aggregator.ts > \
    frontend-services/apps/ui-aggregator/$PROD_FRONT_SUFFIX

sed "s|\$SENSAE_MAPBOX_ACCESS_TOKEN|$1|g" $SECRET_FRONT/fleet-management-frontend.ts > \
        frontend-services/apps/fleet-management-frontend/$FRONT_SUFFIX
sed "s|\$SENSAE_MAPBOX_ACCESS_TOKEN|$1|g" $SECRET_FRONT/fleet-management-frontend.ts > \
        frontend-services/apps/fleet-management-frontend/$PROD_FRONT_SUFFIX

sed "s|\$SENSAE_MAPBOX_ACCESS_TOKEN|$1|g" $SECRET_FRONT/smart-irrigation-frontend.ts > \
        frontend-services/apps/smart-irrigation-frontend/$FRONT_SUFFIX
sed "s|\$SENSAE_MAPBOX_ACCESS_TOKEN|$1|g" $SECRET_FRONT/smart-irrigation-frontend.ts > \
        frontend-services/apps/smart-irrigation-frontend/$PROD_FRONT_SUFFIX

sed "s|\$SENSAE_ADMIN_EMAIL|$4|g" secrets/templates/dev/databases/identity-management-initdb.sql > \
        databases/identity-management-database/identity-management-initdb.sql
