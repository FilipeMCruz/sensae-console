FROM oracle/graalvm-ce:20.1.0-java17
 
ADD . /build
WORKDIR /build
 
# For SDKMAN to work we need unzip & zip
RUN yum install -y unzip zip
 
RUN \
    # Install SDKMAN
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
    # Install Maven
    sdk install maven; \
    # Install GraalVM Native Image
    gu install native-image;
 
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn --version
 
RUN native-image --version

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
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=sharespot-data-decoder-slave-backend,application,domain,infrastructure,endpoint,boot,amqp,ingress,egress,internal,persistence,postgres,domain-services

COPY . .
 
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -B clean package -P native --no-transfer-progress

# We use a Docker multi-stage build here so that we only take the compiled native Spring Boot app from the first build container
FROM oraclelinux:8-slim
 
# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=0 "/build/target/native-image/sharespot-data-decoder-slave-backend" sharespot-data-decoder-slave-backend
 
# Fire up our Spring Boot Native app by default
CMD [ "sh", "-c", "./sharespot-data-decoder-slave-backend" ]

#FROM maven:3.8.3-openjdk-17 AS build
#WORKDIR /app
## get all pom.xml to pull only external dependencies
#COPY application/pom.xml application/pom.xml
#COPY domain/pom.xml domain/pom.xml
#COPY domain-services/pom.xml domain-services/pom.xml
#COPY infrastructure/pom.xml infrastructure/pom.xml
#COPY infrastructure/boot/pom.xml infrastructure/boot/pom.xml
#COPY infrastructure/endpoint/pom.xml infrastructure/endpoint/pom.xml
#COPY infrastructure/endpoint/amqp/pom.xml infrastructure/endpoint/amqp/pom.xml
#COPY infrastructure/endpoint/amqp/ingress/pom.xml infrastructure/endpoint/amqp/ingress/pom.xml
#COPY infrastructure/endpoint/amqp/egress/pom.xml infrastructure/endpoint/amqp/egress/pom.xml
#COPY infrastructure/endpoint/amqp/internal/pom.xml infrastructure/endpoint/amqp/internal/pom.xml
#COPY infrastructure/persistence/pom.xml infrastructure/persistence/pom.xml
#COPY infrastructure/persistence/postgres/pom.xml infrastructure/persistence/postgres/pom.xml
#COPY pom.xml pom.xml
## build all external dependencies
#RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=sharespot-data-decoder-slave-backend,application,domain,infrastructure,endpoint,boot,amqp,ingress,egress,internal,persistence,postgres,domain-services
#
#COPY . .
#RUN mvn clean package
#
#FROM openjdk:17
#WORKDIR /app
#COPY --from=build /app/infrastructure/boot/target/sharespot-data-decoder-slave-backend.war /app
#CMD ["java", "-jar", "sharespot-data-decoder-slave-backend.war"]
