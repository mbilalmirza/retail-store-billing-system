# Retail Store Billing System

## Overview

This project is a Java Spring Boot application designed to calculate the net payable amount for purchases at a retail store. It considers various discount strategies based on user roles (e.g., employee, affiliate, loyal customer) and item categories (e.g., groceries, electronics).

## Features

- Discount calculation based on user roles and purchase history.
- Support for both percentage-based and fixed discounts.
- Exclusion of specific item categories (like groceries) from certain discounts.
- Extensible architecture for adding new discount strategies.

## Prerequisites

- JDK 17 or later
- Maven 3.6 or later (for building and running the project)
- An IDE like IntelliJ IDEA, Eclipse, or Spring Tool Suite (optional)

## Setup and Installation

1. **Clone the Repository**

   ```
   git clone https://github.com/mbilalmirza/retail-store-billing-system.git
   cd retail-store-billing
   ```

2. **Build the Project**

   ```
   mvn clean install
   ```

3. **Run the Application**

   ```
   mvn spring-boot:run
   ```

   Alternatively, you can run the application directly from your IDE by running the `main` method in the `DiscountApplication` class.

## Usage

Describe how to use the application, including any available endpoints if it's a web application, or how to trigger the billing calculation process.

## Testing

Explain how to run the tests for your project.

```
mvn test
```

## Generating Test Coverage Report

Instructions for generating a test coverage report using JaCoCo.

```
mvn verify
```

The coverage report will be generated in `target/site/jacoco/index.html`.

## Generating SonarQube Report

SonarQube analysis was done in IntelliJ only. Its not integrated into Project 

## Contact

```
Muhammad Bilal
m.bilaaal@gmail.com
```