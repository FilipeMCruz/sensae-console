FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
# get all pom.xml to pull only external dependencies
COPY application/pom.xml application/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY domain-services/pom.xml domain-services/pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY infrastructure/boot/pom.xml infrastructure/boot/pom.xml
COPY infrastructure/endpoint/pom.xml infrastructure/endpoint/pom.xml
COPY infrastructure/endpoint/amqp/pom.xml infrastructure/endpoint/amqp/pom.xml
COPY infrastructure/endpoint/amqp/ingress/pom.xml infrastructure/endpoint/amqp/ingress/pom.xml
COPY infrastructure/endpoint/amqp/egress/pom.xml infrastructure/endpoint/amqp/egress/pom.xml
COPY infrastructure/endpoint/amqp/internal/pom.xml infrastructure/endpoint/amqp/internal/pom.xml
COPY infrastructure/persistence/pom.xml infrastructure/persistence/pom.xml
COPY infrastructure/persistence/postgres/pom.xml infrastructure/persistence/postgres/pom.xml
COPY pom.xml pom.xml
# build all external dependencies
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=sharespot-identity-management-slave-backend,application,domain,infrastructure,endpoint,boot,amqp,ingress,egress,internal,persistence,postgres,domain-services

COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/infrastructure/boot/target/sharespot-identity-management-slave-backend.war /app
CMD ["java", "-jar", "sharespot-identity-management-slave-backend.war"]
