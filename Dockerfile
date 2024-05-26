FROM eclipse-temurin:21-jdk-jammy
EXPOSE 8080
COPY "target/smoke-weather-api-0.0.1-SNAPSHOT.jar" smoke-weather-api.jar
ENTRYPOINT ["java","-jar","/smoke-weather-api.jar"]