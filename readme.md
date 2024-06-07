# Jokes Microservice

## Requirements:
- Description:
Service should return jokes via exposed REST GET /jokes endpoint
Parameters: count (int) - count of jokes from 1 to 100, default is 5, 
if number of jokes exceeded then service should return 
"You can get no more than 100 jokes at a time" with http status 400
if 100 requests are already done in the latest 15 minutes you should receive:
- Below are some extra problems found that needs special handling:
-   2 extra validations are required (DONE)
"During the multiple requests you are allowed to obtain 100 jokes in the last 15 minutes interval"
if currently N < 100 requests in the latest 15 minutes are done and in the same time
interval you receive a call with a count that exceeds 100 requests
"At this time you are only allowed to a number of request equal to = X"
X = N + count - 100
-   there is a problem with /random_joke api because it has some special curly characters (IN PROGRESS)
These characters should be filtered out before returning the result of GET /jokes endpoint
For getting Jokes candidate have to use endpoint:
https://official-joke-api.appspot.com/random_joke
If more than one joke is requested, they must be requested in
batches of 10 in parallel (i.e. 10 in parallel, then another 10 in parallel, etc.).
Latest received Jokes should be stored in the noSQL storage

1. Non-functional requirements:
1.1. The code should be logically divided into packages and classes, and conform basic design principles
2.1. The code must be covered with tests.
3.1. Use "keep it simple" approach
4.1. Candidate should use noSQL storage - such as Redis
5.1. Integration test should be provided

2. Time limit:
2.1. 8 hours. (Extra time for the latest 2 validations and local configuration if needed). 

3. gitHub instructions:
3.1. Create your own gitHub account (if you don't have it) 2.
3.2. Make sure that your gitHub Profile contains at least your name and surname
3.3. Create private repo (for example: "java-jokes")
3.4. Create branch "exercise-impl"
3.5. Implement Assignment in branch “exercise-impl”

## Some details regarding implementation.
- The following "Windows" version of Redis was used: Redis-x64-3.0.504.msi which can be installed from https://github.com/microsoftarchive/redis/releases
- OpenJDK Java version can be downloaded from: https://jdk.java.net/java-se-ri/19
- Some useful commands regarding Redis can be learned here: https://stackoverflow.com/questions/8078018/get-redis-keys-and-values-at-command-prompt
- As this is a POC, the main focus is solving the task properly and offering good coverage as well as relevant integration tests.
- Possible improvements:
- a) Refactor code so that it doesn't use the PersistenceService instead it uses the JPA Data template Repository class.
- b) Use a separate database for integration tests.
- c) Adjust code to its newer versions and use newer features if better / simpler / more performant.
- d) Add a logging configuration eventually a LOG4J.
- e) Add JACOCO to see the coverage, make sure we have full coverage.
- f) Add OpenApi (SWAGGER 3.0) to see API documentation.
- g) Add project specific Error classes (children of Runtime exception) in an exception package
- h) Improve this readme.md if needed.

## How to Redis
- redis-cli keys * lists persisted keys
- type <KEY>     * returns the type of the key
-   for type "string": get <key>
-   for type "hash": hgetall <key>
-   for type "list": lrange <key> 0 -1
-   for type "set": smembers <key>
-   for type "zset": zrange <key> 0 -1 withscores

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