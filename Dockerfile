# FROM openjdk:17-jdk-alpine as builder
# # WORKDIR extracted
# ADD ./build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
# RUN java -Djarmode=layertools -jar app.jar extract

# FROM openjdk:17-jdk-alpine
# WORKDIR application
# COPY --from=builder extracted/dependencies/ ./
# COPY --from=builder extracted/spring-boot-loader/ ./
# COPY --from=builder extracted/snapshot-dependencies/ ./
# COPY --from=builder extracted/application/ ./

# EXPOSE 8080

# ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
# ------------------------------------------------
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]