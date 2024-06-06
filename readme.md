# Jokes Microservice

## Some details regarding implementation.
- Due extreme time limitations and errors on Docker point 4. from Non-Functional requirements was not implemented.
- Provided rest endpoint - /random_joke - has several issues regarding special curly quotes and double quotes (see code for more details)
- The following windows version of Redis was used: Redis-x64-3.0.504.msi which can be installed from https://github.com/microsoftarchive/redis/releases
- OpenJDK Java version can be downloaded from: https://jdk.java.net/java-se-ri/19
- Some useful commands regarding Redis can be learned here: https://stackoverflow.com/questions/8078018/get-redis-keys-and-values-at-command-prompt
- I focused mostly on solving the test properly and offering good coverage as well as relevant integration tests.
- Things that could be improved but weren't due to time limitations were:
- a) Implementation of point 4 and figuring out why Docker doesn't work for windows and why it fails to start
-    eventually searching about error and how to properly set its configuration file
- b) Refactor code so it doesn't use the RedisTemplate in PersistenceService but directly uses the JPA Data template
-    and removes the need of having to write manually queries for simple operations
- c) As this is a POC and it virtually only saves one record with its latest jokes and doesn't set all the previous
-    jokes (default implementation without DELETE). A question could rise here about how it should work either store
-    everything or the existing behavior is ok.
- d) Use a separate in memory redis database for integration tests instead of reusing the same "PRODUCTION" database.
- e) Adjust code to its newer versions and use newer features if better / simpler / more performant.
- f) Add a logging LOG4J system
- g) Add JACOCO to see the coverage, should be above 90% per average but still as it is the project doesn't reveal it\
- h) Add OpenApi (SWAGGER 3.0) to see API documentation in a similar manner as my public "webservice" project that is
-    fully covered (100% coverage) and which also has JACOCO integrated.
- i) Once JACOCO is integrated add remaining missing unit tests.
- j) Add project specific Error classes (children of Runtime exception) in an exception package
- k) We could even add something basic such "Basic Authentication" (however this particular topic is outside requirements)
- l) Improve this readme.md if needed.

## Description
This microservice returns jokes over an HTTP REST API.

## Requirements
- Java 19
- Maven
- Redis
- Intellij IDEA 2023 Community Edition

## How to Build and Run

1. Clone the repository:
    ```sh
    git clone <repository-url>
    ```

2. Navigate to the project directory:
    ```sh
    cd java-jokes
    ```

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run Redis server (if not already running):
    ```sh
    redis-server
    ```

5. Run the application:
    ```sh
    mvn spring-boot:run
    ```

6. Access the jokes endpoint:
    ```sh
    http://localhost:8080/jokes?count=5
    ```

## Running Tests
To run the tests, use the following command:
```sh
mvn test