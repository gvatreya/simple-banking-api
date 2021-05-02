# Finmid Banking API

This project contains the backend written in Java 11(LTS) for a sample Banking application.
The supported operations are `Account CRUD` and `Transactions`.

### Access the Dockerized API
The API is dockerized and published to docker hub. 

To start the application using the docker image, Run the command 

`docker run -p 8080:8080 gvatreya/finmid-api-docker`

One can access the Open API doc by running [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Also, one can make use of the postman collection attached (`Finmid_Banking.postman_collection.json`), to start playing with the APIs.

**NOTE**: Since the application uses in-memory H2 database, the data is not persisted, 
meaning a fresh database is available, for every new run. 


### System Requirements
- Java 11
- Maven

### Instructions to run the application manually using the codebase
Please refer to the system requirements above, before running these commands. <br/><br/>

1. Clone the repository
1. From the directory, where the project is cloned, run the project by executing the command `./mvnw spring-boot:run`
1. To run the tests, use the command `./mvnw test`

### API Specification
The project exposes it's API specification using the OpenAPI standards.
One can access the API doc at `http://<base-url>/swagger-ui.html`.
For example: `http://localhost:8080/swagger-ui.html`

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

