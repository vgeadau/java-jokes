# Jokes Microservice

## Some details regarding implementation.
- Due extreme time limitations and errors on Docker point 4. from Non-Functional requirements was not implemented.
- Provided rest endpoint - /random_joke - has several issues regarding special curly quotes and double quotes (see code for more details)
- The following windows version of Redis was used: Redis-x64-3.0.504.msi which can be installed from https://github.com/microsoftarchive/redis/releases
- OpenJDK Java version can be downloaded from: https://jdk.java.net/java-se-ri/19
- Some usefull commands regarding Redis can be learned here: https://stackoverflow.com/questions/8078018/get-redis-keys-and-values-at-command-prompt

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