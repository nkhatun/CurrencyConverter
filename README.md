# CurrencyConverter

The goal of this test assignment is to demonstrate an "Exchange Rate Service". The service
exposes an API, which can be consumed by a frontend application.

## User Stories
As a user, who accesses this service through a user interface, ...
1. I want to retrieve the ECB reference rate for a currency pair, e.g. USD/EUR or
HUF/EUR.
2. I want to retrieve an exchange rate for other pairs, e.g. HUF/USD.
3. I want to retrieve a list of supported currencies and see how many times they were
requested.
4. I want to convert an amount in a given currency to another, e.g. 15 EUR = ??? GBP
5. I want to retrieve a link to a public website showing an interactive chart for a given
currency pair.

## Implementation
The European Central Bank publishes reference exchange rates on a daily basis.
You can find them here:
**https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html**

## Technology
- **Spring Boot**     - Server side framework
- **Actuator**        - Application insights on the fly
- **Junit**           - Unit testing framework
- **jsoup**           - java html parser
- **Java**            - 8

## Application Set up and Build Steps
**Backend** 
Run the below command from the root directory
1. `mvn clean install`

## Running the server locally
Run the below command from the root directory
1. `mvn spring-boot:run`
URL: http://<host-name>:8888/
  
## Unit test cases
Run the below command from the root directory
1. `mvn clean test`

## Time Spent
4 hr

## ENHANCEMENT SCOPE
  1. Microservices
  2. Swagger documentation
  3. Spring security
  4. Dockerizing the application
  5. Performance Testing
  6. Frontend Integration
  
