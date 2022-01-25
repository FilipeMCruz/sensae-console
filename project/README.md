# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker to run and docker-compose to orchestrate the containers.

## DEV Environment

This environment is manly used by developers running `linux`,`mac` or a `bsd` system (windows is currently not supported) and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers (if existing).
This way the frontend and backend services can be started by the programmer on debug mode if and when needed.

Every external interaction with the environment is secured with `ssl`. To run the environment locally an `ssl certificate` has to be generated.
It is advised to use the program `openssl`.
Run the following commands to create the certificate `crt` and `key`.

``` sh
openssl req -x509 -out nginx.crt -keyout nginx.key \
  -newkey rsa:2048 -nodes -sha256 \
  -subj '/CN=localhost' -extensions EXT -config <( \
   printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n[EXT]\nsubjectAltName=DNS:localhost\nkeyUsage=digitalSignature\nextendedKeyUsage=serverAuth")
sudo mv nginx* /etc/nginx/ssl/
```

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

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace any identified tag (`<this is a tag>`).

File: `project/frontend-services/apps/sharespot-location-tracking-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    websocket: "ws://localhost:8086/subscriptions",
    http: "http://localhost:8086/graphql"
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
```

File: `project/frontend-services/apps/sharespot-device-records-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    http: 'http://localhost:8085/graphql'
  }
};
```

File: `project/frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    http: 'http://localhost:8083/graphql'
  }
};
```

File: `project/frontend-services/apps/sharespot-simple-auth-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    http: 'http://localhost:8090/graphql'
  }
};
```

File: `project/frontend-services/apps/ui-aggregator/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    deviceRecords: {
      backendURL: {
        http: 'http://localhost:8085/graphql'
      }
    },
    dataProcessor: {
      backendURL: {
        http: 'http://localhost:8083/graphql'
      }
    },
    locationTracking: {
      backendURL: {
        websocket: "ws://localhost:8086/subscriptions",
        http: "http://localhost:8086/graphql"
      },
    },
    simpleAuth: {
      backendURL: {
        http: 'http://localhost:8090/graphql'
      }
    }
  }
};
```

File: `project/secrets/dev/sharespot-common-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-records and data-processor master backend>
```

File: `project/secrets/dev/sharespot-data-store-database.env`

``` conf
MONGO_INITDB_ROOT_USERNAME=root
MONGO_INITDB_ROOT_PASSWORD=<root database password>
MONGO_INITDB_DATABASE=data
```

File: `project/secrets/dev/init-sharespot-data-store-database.env`

``` js
db.createUser({
  user: "user",
  pwd: "<key to exchange with data-store>",
  roles: [{ role: "readWrite", db: "data" }],
});
```

File: `project/backend-services/sharespot-chrono-data-store/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8086

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG

spring.datasource.url=jdbc:postgresql://localhost:8812/qdb?sslmode=disable
spring.datasource.username=admin
spring.datasource.password=quest
```

`Important`: Chrono data store is currently not in use.

File: `project/backend-services/sharespot-data-processor-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8083

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/transformations
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql
```

File: `project/backend-services/sharespot-data-processor-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8081

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/transformations
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-data-store/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8087

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG

spring.data.mongodb.database=data
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
spring.data.mongodb.username=user
spring.data.mongodb.password=<key to exchange with data store database mongodb> 
```

File: `project/backend-services/sharespot-device-records-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8085

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql
```

File: `project/backend-services/sharespot-device-records-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8085

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-data-gateway/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8080

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

sharespot.auth.key=<auth key to authenticate requests>
```

File: `project/backend-services/sharespot-data-validator-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8093

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `project/backend-services/sharespot-location-tracking-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8085

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE

spring.datasource.url=jdbc:postgresql://localhost:8812/qdb?sslmode=disable
spring.datasource.username=admin
spring.datasource.password=quest
```

File: `project/backend-services/sharespot-simple-auth-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8090

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE

sharespot.simple.auth.user.name=<user name>
sharespot.simple.auth.user.secret=<user password>
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

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`). All `environment.prod.ts` need it's associated `environment.ts` config.

File: `project/frontend-services/apps/sharespot-location-tracking-frontend/src/environments/environment.prod.ts`

``` ts
export const environment = {
  production: true,
  backendURL: {
    websocket: "wss://localhost/location-tracking/subscriptions",
    http: "https://localhost/location-tracking/graphql"
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
```

File: `project/frontend-services/apps/sharespot-device-records-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  backendURL: {
    http: "https://localhost/device-records/graphql"
  }
};
```

File: `project/frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  backendURL: {
    http: "https://localhost/data-processor/graphql"
  }
};
```

File: `project/frontend-services/apps/sharespot-simple-auth-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  backendURL: {
    http: "https://localhost/simple-auth/graphql"
  }
};
```

File: `project/frontend-services/apps/ui-aggregator/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    deviceRecords: {
      backendURL: {
        http: 'https://localhost/device-records/graphql',
      },
    },
    dataProcessor: {
      backendURL: {
        http: 'https://localhost/data-processor/graphql'
      }
    },
    locationTracking: {
      backendURL: {
        websocket: 'wss://localhost/location-tracking/subscriptions',
        http: 'https://localhost/location-tracking/graphql',
      },
    },
    simpleAuth: {
      backendURL: {
        http: 'https://localhost/simple-auth/graphql'
      }
    }
  },
};
```

File. `project/secrets/prod/init-sharespot-data-store-database.js`

``` js
db.createUser({
  user: "user",
  pwd: "<key to exchange with data-store>",
  roles: [{ role: "readWrite", db: "data" }],
});
```

File. `project/secrets/prod/sharespot-chrono-data-store-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://questdb:8812/qdb?sslmode=disable
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=quest
```

`Important`: Chrono data store is currently not in use.

File. `project/secrets/prod/sharespot-common-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-records and data-processor master backend>
```

File. `project/secrets/prod/sharespot-data-processor-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/transformations
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-processor-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/transformations
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-store-backend.env`

``` conf
SPRING_DATA_MONGODB_DATABASE=data
SPRING_DATA_MONGODB_PORT=27017
SPRING_DATA_MONGODB_HOST=sharespot-data-store-database
SPRING_DATA_MONGODB_USERNAME=user
SPRING_DATA_MONGODB_PASSWORD=<key to exchange with data-store>
```

File. `project/secrets/prod/sharespot-data-store-database.env`

``` conf
MONGO_INITDB_ROOT_USERNAME=root
MONGO_INITDB_ROOT_PASSWORD=<key to exchange with data-store>
MONGO_INITDB_DATABASE=data
```

File. `project/secrets/prod/sharespot-device-records-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-device-records-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-gateway.env`

``` conf
SHARESPOT_AUTH_KEY=<auth key to authenticate requests>
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File. `project/secrets/prod/sharespot-location-tracking-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
SPRING_DATASOURCE_URL=jdbc:postgresql://questdb:8812/qdb?sslmode=disable
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=quest
```

File. `project/secrets/prod/sharespot-simple-auth-backend.env`

``` conf
SHARESPOT_SIMPLE_AUTH_USER_NAME=<user name>
SHARESPOT_SIMPLE_AUTH_USER_SECRET=<user password>
```

File. `project/secrets/prod/sharespot-data-validator-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```
