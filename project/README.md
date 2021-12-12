# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker to run and docker-compose to orchestrate the containers.

## DEV Environment

This environment is manly used by developers and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers (if existing).
This way the backend services can be started by the programmer on debug mode if and when needed.

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace any identified tag (`<this is a tag>`).

File: `project/frontend-services/apps/sharespot-location-tracking-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    websocket: "ws://localhost:8084/subscriptions",
    http: "http://localhost:8084/graphql"
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
    http: 'http://localhost:8083/graphql'
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
        http: 'http://localhost:8083/graphql'
      }
    },
    locationTracking: {
      backendURL: {
        websocket: "ws://localhost:8084/subscriptions",
        http: "http://localhost:8084/graphql"
      },
    }
  }
};
```

File: `project/backend-services/sharespot-lgt92-gps-data-gateway/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8080

auth.key=<helium defined auth key>

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `project/backend-services/sharespot-lgt92-gps-data-processor/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8081

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `project/backend-services/sharespot-device-records-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8083

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with device-records database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql
```

File: `project/backend-services/sharespot-device-records-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8082

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with device-records database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-location-tracking-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8084

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE
```

File: `project/secrets/dev/sharespot-device-records-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-records backend>
POSTGRES_DB=records
```

## PROD Environment

This environment is used in production and can be started with (after creating the files below):

``` sh
docker-compose up -d
```

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`).

File: `project/frontend/sharespot-location-tracking-frontend/src/environments/environment.prod.ts`

``` ts
export const environment = {
  production: true,
  backendURL: {
    websocket: "ws://localhost/subscriptions",
    http: "http://localhost/graphql"
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
    http: "http://localhost/device-records/graphql"
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
        http: 'http://localhost/device-records/graphql',
      },
    },
    locationTracking: {
      backendURL: {
        websocket: 'ws://localhost/location-tracking/subscriptions',
        http: 'http://localhost/location-tracking/graphql',
      },
    },
  },
};
```

File: `project/secrets/prod/sharespot-lgt92-gps-data-gateway.env`

``` conf
AUTH_KEY=<helium defined auth key>
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `project/secrets/prod/sharespot-location-tracking-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `project/secrets/prod/sharespot-lgt92-gps-data-processor.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `project/secrets/prod/sharespot-device-records-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-records backend>
```

File: `project/secrets/prod/sharespot-device-records-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-device-records-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with device-records backend>
```

File: `project/secrets/prod/sharespot-device-records-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-device-records-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with device-records backend>
```
