# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker to run and docker-compose to orchestrate the containers.

Current version:

- `system` : `0.10.0`

## DEV Environment

This environment is manly used by developers running `linux`,`mac` or a `bsd` system (windows is currently not supported) and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers (if existing).
This way the frontend and backend services can be started by the programmer on debug mode if and when needed.

Every external interaction with the environment is secured with `ssl`. To run the environment locally an `ssl certificate` has to be generated.
It is advised to use `openssl`.
Run the following commands to create the certificate `crt` and `key`.

``` sh
openssl req -x509 -out nginx.crt -keyout nginx.key \
  -newkey rsa:2048 -nodes -sha256 \
  -subj '/CN=localhost' -extensions EXT -config <( \
   printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n[EXT]\nsubjectAltName=DNS:localhost\nkeyUsage=digitalSignature\nextendedKeyUsage=serverAuth")
sudo mv nginx* /etc/nginx/ssl/
```

The endpoints of each backend service are secured with JWT. This token is generated/validated internally by this services. To generate valid and secure JWTs a X509 certificate is used. This certificate keys, private and public, have to be generated, to do so follow [this](https://www.baeldung.com/java-rsa).

To run every frontend service run:

``` sh
cd project/frontend-services
nx serve-mfe
```

To run a single frontend service run:

``` sh
cd project/frontend-services
nx serve <frontend-name>
```

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords. For that create the following file and fill each property:

File: `project/secrets/dev.conf`

``` conf
export SENSAE_MAPBOX_ACCESS_TOKEN=
export SENSAE_MAPBOX_SIMPLE_STYLE=
export SENSAE_MAPBOX_SATELLITE_STYLE=

export SENSAE_BROKER_USERNAME=
export SENSAE_BROKER_PASSWORD=

export SENSAE_COMMON_DATABASE_PASSWORD=
export SENSAE_DATA_STORE_USER_PASSWORD=
export SENSAE_DATA_STORE_ROOT_PASSWORD=

export SENSAE_AUTH_PATH_PUB_KEY=
export SENSAE_AUTH_PATH_PRIV_KEY=
export SENSAE_AUTH_ISSUER=
export SENSAE_AUTH_AUDIENCE=
export SENSAE_DATA_AUTH_KEY=
export SENSAE_AUTH_EXTERNAL_MICROSOFT_AUDIENCE=
export SENSAE_AUTH_EXTERNAL_GOOGLE_AUDIENCE=

export SENSAE_SMS_TWILIO_ACCOUNT_SID=
export SENSAE_SMS_TWILIO_AUTH_TOKEN=
export SENSAE_SMS_SENDER_NUMBER=
export SENSAE_SMS_ACTIVATE=

export SENSAE_EMAIL_SENDER_ACCOUNT=
export SENSAE_EMAIL_SUBJECT=
export SENSAE_EMAIL_SENDER_PASSWORD=
export SENSAE_EMAIL_SMTP_HOST=
export SENSAE_EMAIL_SMTP_PORT=
export SENSAE_EMAIL_ACTIVATE=
export SENSAE_ADMIN_EMAIL=
```

And run:

``` sh
./project/scripts/generate-dev-config.sh
```

## PROD Environment

This environment is used in production and can be started with (after creating the files below):

``` sh
./project/scripts/reset-prod.sh
```

Every external interaction with the environment is secured with `ssl`. To run the environment in production an `ssl certificate` has to be generated.
It is advised to use the program `certbot`.
Run the following commands to create the certificate `crt` and `key`.

``` sh
sudo certbot --nginx -d <yourdomain>
mkdir /etc/nginx/ssl
ln -s /etc/letsencrypt/live/<yourdomain>/fullchain.pem /etc/nginx/ssl/nginx.crt
ln -s /etc/letsencrypt/live/<yourdomain>/privkey.pem /etc/nginx/ssl/nginx.key
```

The endpoints of each backend service are secured with JWT. This token is generated/validated internally by this services. To generate valid and secure JWTs a X509 certificate is used. This certificate keys, private and public, have to be generated, to do so follow [this](https://www.baeldung.com/java-rsa).

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords. For that create the following file and fill each property (the dev file also needs to be created):

File: `project/secrets/prod.conf`

``` conf
export SENSAE_MAPBOX_ACCESS_TOKEN=
export SENSAE_MAPBOX_SIMPLE_STYLE=
export SENSAE_MAPBOX_SATELLITE_STYLE=

export SENSAE_BROKER_USERNAME=
export SENSAE_BROKER_PASSWORD=

export SENSAE_COMMON_DATABASE_PASSWORD=
export SENSAE_DATA_STORE_USER_PASSWORD=
export SENSAE_DATA_STORE_ROOT_PASSWORD=

export SENSAE_AUTH_PATH_PUB_KEY=
export SENSAE_AUTH_PATH_PRIV_KEY=
export SENSAE_AUTH_ISSUER=
export SENSAE_AUTH_AUDIENCE=
export SENSAE_DATA_AUTH_KEY=
export SENSAE_AUTH_EXTERNAL_MICROSOFT_AUDIENCE=
export SENSAE_AUTH_EXTERNAL_GOOGLE_AUDIENCE=

export SENSAE_SMS_TWILIO_ACCOUNT_SID=
export SENSAE_SMS_TWILIO_AUTH_TOKEN=
export SENSAE_SMS_SENDER_NUMBER=
export SENSAE_SMS_ACTIVATE=

export SENSAE_EMAIL_SENDER_ACCOUNT=
export SENSAE_EMAIL_SUBJECT=
export SENSAE_EMAIL_SENDER_PASSWORD=
export SENSAE_EMAIL_SMTP_HOST=
export SENSAE_EMAIL_SMTP_PORT=
export SENSAE_EMAIL_ACTIVATE=

export SENSAE_PROD_PUBLIC_DOMAIN=

export SENSAE_ADMIN_EMAIL=
```

And run:

``` sh
./project/scripts/generate-dev-config.sh
./project/scripts/generate-prod-config.sh
```
