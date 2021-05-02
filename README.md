# Finmid Banking API

This project contains the backend written in Java 11(LTS) for a sample Banking application.
The supported operations are `Account CRUD` and `Transactions`.

### System Requirements
- Java 11
- Maven

### Running instructions
One can run the project by executing the command `./mvnw spring-boot:run` 
from the directory, where the project is cloned.

To run the tests, one can use the command `./mvnw test`

### API Specification
The project exposes it's API sepcification using the OpenAPI standards.
One can access the API doc at `http://<base-url>/swagger-ui.html`.
For example: `http://localhost:8081/swagger-ui.html`

### Additional Notes

###### Create Account API - Special Case (For other APIs please refer the OpenAPI spec)
The create accounts API takes the following JSON as input
```json
{
  "accountId": <number>,
  "balance": <number>
}
```
The API checks the property `balance.use.default` and when set, ignores the balance attribute 
from above and instead creates account with default balance defined by the property `balance.default`

The project makes use of certain additional libraries and/or utilities, detailed below.

###### lombok
This project uses [Project Lombok](https://projectlombok.org) to help with bolierplate code.

###### H2 In-Memory Database
H2 in memory database is the main database used in the application, for simplicity.

