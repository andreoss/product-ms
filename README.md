# product-ms
[![Build Status](https://travis-ci.org/andreoss/product-ms.svg?branch=master)](https://travis-ci.org/andreoss/product-ms)

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

