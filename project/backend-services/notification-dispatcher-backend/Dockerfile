FROM maven:3.8.5-openjdk-18 AS build
WORKDIR /app
# get all pom.xml to pull only external dependencies
COPY application/pom.xml application/pom.xml
COPY domain/pom.xml domain/pom.xml
COPY infrastructure/boot/pom.xml infrastructure/boot/pom.xml
COPY infrastructure/endpoint/pom.xml infrastructure/endpoint/pom.xml
COPY infrastructure/endpoint/amqp/pom.xml infrastructure/endpoint/amqp/pom.xml
COPY infrastructure/endpoint/sms/pom.xml infrastructure/endpoint/sms/pom.xml
COPY infrastructure/endpoint/email/pom.xml infrastructure/endpoint/email/pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY pom.xml pom.xml
# build all external dependencies
RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline -DexcludeArtifactIds=notification-dispatcher-backend,application,domain,infrastructure,endpoint,boot,amqp,email,sms

COPY . .
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/infrastructure/boot/target/notification-dispatcher-backend.war /app
CMD ["java", "-jar", "notification-dispatcher-backend.war"]
