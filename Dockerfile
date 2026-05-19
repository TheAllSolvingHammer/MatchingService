#FROM maven:3.9.8-eclipse-temurin-21 AS build
#WORKDIR /build
#
## 1. Copy the service-level pom.xml
#COPY pom.xml .
#
## 2. Copy all sub-module folders
#COPY api ./api
#COPY persistence ./persistence
#COPY core ./core
#COPY web ./web
#
## 3. Build the entire project. This creates JARs for all modules.
#RUN mvn clean package -DskipTests
#
## --- Stage 2: Run Stage ---
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#
## 4. Copy ONLY the executable JAR from the 'web' module's target folder
#COPY --from=build /build/web/target/*.jar app.jar
#
#EXPOSE 8084
#ENTRYPOINT ["java", "-jar", "app.jar"]
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY web/target/*.jar app.jar
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
