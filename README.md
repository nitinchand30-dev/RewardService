# RewardService

Sample assignment for Sr. Software Engineer @TELUS International.

## Overview

RewardService is a Spring Boot based REST API application that calculates reward points earned by customers based on their purchase transactions.

The application evaluates customer transactions for a 3-month period and returns:

* Monthly reward points per customer
* Total reward points per customer
* Aggregated reward details

## Reward Calculation Logic

Customers earn reward points based on the following rules:

* 2 points for every dollar spent over $100
* 1 point for every dollar spent between $50 and $100

### Example

For a transaction of $120:

* Amount between $50 and $100 = 50 points
* Amount above $100 = 20 × 2 = 40 points

Total reward points = 90

## Tech Stack

* Java 17
* Spring Boot 3.2.5
* Spring Web
* Spring Data JPA
* H2 Database
* Maven
* JUnit & Mockito

## Project Structure

```text
src/main/java/com/telusAssignment/rewards
│
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
├── serviceImpl
└── util
```

## API Endpoint

### Get Reward Points

```http
GET /api/rewards
```

### Sample Response

```json
{
  "success": true,
  "message": "Rewards calculated successfully",
  "data": {
    "customerRewards": [],
    "totalCustomers": 0,
    "totalRewards": 0
  },
  "statusCode": 200
}
```

## H2 Database

The application uses an in-memory H2 database.

### H2 Console URL

```text
http://localhost:8080/h2-console
```

### JDBC URL

```text
jdbc:h2:mem:rewardsdb
```

## Sample Data

Sample transaction data is available in:

```text
src/main/resources/Data.sql
```

## Running the Application

### Clone Repository

```bash
git clone https://github.com/nitinchand30-dev/RewardService.git
```

### Navigate to Project

```bash
cd RewardService
```

### Run Using Maven

```bash
mvn spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

## Running Tests

```bash
mvn test
```

## Unit Testing

The project includes unit test cases for:

* Controller layer
* Reward calculation flow
* Exception scenarios

Testing frameworks used:

* JUnit 5
* Mockito
* MockMvc

## JavaDocs

The project includes JavaDocs for:

* Controllers
* Services
* DTOs
* Repository layer
* Exception handling

### Sample JavaDoc

```java
/**
 * REST Controller for managing rewards-related endpoints.
 * Provides API endpoints for calculating and retrieving customer rewards.
 */
@RestController
@RequestMapping("/api")
public class RewardsController {

    /**
     * Get calculated rewards for all customers.
     *
     * @return ResponseEntity containing reward details
     */
    @GetMapping("/rewards")
    public ResponseEntity<ApiResponse<RewardsResponseDto>> getRewards() {
        return null;
    }
}
```


## GitHub Repository

GitHub Repository:

[https://github.com/nitinchand30-dev/RewardService.git](https://github.com/nitinchand30-dev/RewardService.git)

## Author

Nitin Chand
