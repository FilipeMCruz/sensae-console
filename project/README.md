# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker and docker-compose to run.

## DEV Environment

This environment is manly used by developers and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers.
This way the backend services can be started by the programmer on debug mode if and when needed.

All communications between backend services and databases are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`).

File: `backend-services/location-tracking-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8082

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

## PROD Environment

This environment is used in production and can be started with (after creating the files below):

``` sh
docker-compose up -d
```

All communications between backend services and databases are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`).

File: `secrets/location-tracking-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```
