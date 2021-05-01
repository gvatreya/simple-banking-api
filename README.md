# Finmid Banking API

#### Additional Notes

##### Create Account API
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

