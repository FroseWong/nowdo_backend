# ----------- BUILD STAGE -------------
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ----------- RUN STAGE -------------
FROM openjdk:17-alpine
COPY --from=build /app/target/nowdo-backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]


# FROM openjdk:8-jdk-alpine
# COPY target/nowdo-backend.jar nowdo-backend.jar
# ENTRYPOINT ["java -jar /nowdo-backend.jar"]