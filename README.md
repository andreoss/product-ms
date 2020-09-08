# product-ms
[![Build Status](https://travis-ci.org/andreoss/product-ms.svg?branch=master)](https://travis-ci.org/andreoss/product-ms)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=andreoss_product-ms&metric=alert_status)](https://sonarcloud.io/dashboard?id=andreoss_product-ms)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=andreoss_product-ms&metric=coverage)](https://sonarcloud.io/dashboard?id=andreoss_product-ms)

Sample microservice which uses Spring Data JDBC and has REST endpoint

# Build
```
./gradlew clean install
```

# Run (on embedded database)
```
SPRING_PROFILES_ACTIVE=h2 ./gradlew bootRun
```
And access REST endpoint at http://localhost:8080/products


# Run on Docker
```
$ TODO
```

