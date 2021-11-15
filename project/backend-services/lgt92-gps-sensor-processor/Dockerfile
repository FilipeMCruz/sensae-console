FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
# get all pom.xml to pull only external dependencies
COPY application/pom.xml application/pom.xml
COPY infrastructure/boot/pom.xml infrastructure/boot/pom.xml
COPY infrastructure/endpoint/pom.xml infrastructure/endpoint/pom.xml
COPY infrastructure/endpoint/amqp-egress/pom.xml infrastructure/endpoint/amqp-egress/pom.xml
COPY infrastructure/endpoint/amqp-ingress/pom.xml infrastructure/endpoint/amqp-ingress/pom.xml
COPY infrastructure/mapper/pom.xml infrastructure/mapper/pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY pom.xml pom.xml
# build all external dependencies
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=sharespot-lgt92-gps-sensor-processor,application,infrastructure,endpoint,amqp-ingress,boot,amqp-egress,mapper

COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/infrastructure/boot/target/sharespot-lgt92-gps-sensor-processor.war /app
CMD ["java", "-jar", "sharespot-lgt92-gps-sensor-processor.war"]
