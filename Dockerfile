FROM maven:3.8.6-openjdk-11 AS builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM tomee:9-jre11

LABEL maintainer="ZTI Project"

COPY --from=builder /app/target/ZTI-1.0-SNAPSHOT.war /usr/local/tomee/webapps/

EXPOSE 8080
