# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker to run and docker-compose to orquestraste the containers.

## DEV Environment

This environment is manly used by developers and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers (if existing).
This way the backend services can be started by the programmer on debug mode if and when needed.

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace any identified tag (`<this is a tag>`).

File: `backend-services/lgt92-gps-sensor-gateway/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8080

auth.key=<helium defined auth key>

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `backend-services/lgt92-gps-sensor-processor/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8081

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `backend-services/location-tracking-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8082

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.com.example=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE
```

File: `frontend/location-tracking-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  backendURL: {
    websocket: "ws://localhost:8082/subscriptions",
    http: "http://localhost:8082/graphql"
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
};

```

## PROD Environment

This environment is used in production and can be started with (after creating the files below):

``` sh
docker-compose up -d
```

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`).

File: `secrets/lgt92-gps-sensor-gateway.env`

``` conf
AUTH_KEY=<helium defined auth key>
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `secrets/lgt92-gps-sensor-processor.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `secrets/location-tracking-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File: `frontend/location-tracking-frontend/src/environments/environment.ts`

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
