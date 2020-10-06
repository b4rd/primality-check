# Primality checker REST service using Spring Boot 

### Building the app
```console
./mvnw package
```

### Running the app
```console
./mvnw spring-boot:run
```

### Running tests

Execute `mvnw verify` to run unit tests. To run both unit and integration tests, use the `integration` profile: 
```console
./mvnw verify -P integration
```

### Building a Docker image
After building the app, run:
```console
docker build -t demo/primality-check .
```

### Running the container
```console
docker run -p 8080:8080 demo/primality-check
```

Alternatively, you could use the image from the public registry:
```console
docker run -p 8080:8080 jakkelp/primality-check
```

### Using the app
The application has a single GET endpoint you can call:
```console
curl -s localhost:8080/primality-check?number=5
```

The response should look like this:
```json
{"nextPrime":5,"isPrime":true}
```
