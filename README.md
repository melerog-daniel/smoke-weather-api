# Smoke Weather API

Smoke Weather API is a microservice for provide temperature information based on
location retrieving the data from [Open-Meteo API](https://open-meteo.com/). This app has been developed in Java (
v21.0.3) with SpringBoot (v3.3.0) framework.

## Usage

Use the following command to build the microservice docker image.

```bash
docker build -t smoke-weather-api:latest .
```

There is a [docker-compose.yaml](docker-compose.yaml) where this docker image is integrated with kafka and mongoDB.

The below command is required to run the whole infrastructure together with the microservice.

```bash
docker compose up -d
```

## Documentation

[Swagger UI](http://localhost:8080/api/v1/swagger-ui/index.html) has been implemented to document the endpoints of the
application. After each build of the microservice, the OpenAPI documentation is automatically updated depending on the
changes made to the endpoints.

## Kafka

The microservice performs event-driven communication via Kafka. It connects to the specified Kafka server through
environment variables, producing on the required topic.

## Environment config

Spring profiles have been created to manage the environment variables, adapting the microservice for
both [local](src/main/resources/application-local.yaml) and [dockerised](src/main/resources/application-prod.yaml)
execution.

## Test

The unit tests of the services have been implemented. A [coverage report](src/main/resources/static/coverageReport.pdf)
has been exported as PDF file.
