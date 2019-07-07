FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM openjdk:8-jdk-alpine

LABEL maintainer="mark@markpost.xyz"
VOLUME /tmp
EXPOSE 9000

COPY --from=build /workspace/app/target/BankDemo-*.jar BankDemo.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","BankDemo.jar"]
