# Visa Transaction Simulator

This project simulates Visa transactions by implementing ISO 8583 messages and using a Visa-specific XML packager.

## Table of Contents
- [Overview](#overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Accessing API Documentation (Swagger UI)](#accessing-api-documentation-swagger-ui)
- [Usage](#usage)
- [ISO 8583 Message Types](#iso-8583-message-types)
- [Visa XML Packager](#visa-xml-packager)
- [Contributing](#contributing)
- [License](#license)

## Overview

This simulator allows you to generate and process ISO 8583 messages for Visa transactions. It uses a Visa-specific XML packager to format the messages according to Visa's specifications. The project is built with Spring Boot and utilizes the jPOS library for handling ISO 8583 messages.

## Prerequisites

To run this Visa Transaction Simulator, you'll need the following:

* Java Development Kit (JDK) 22 or higher
* Maven 3.6 or higher (for managing dependencies and building the project)
* Access to Visa's XML packager file (visa.xml)
* MongoDB (for data storage)

The project uses the following main dependencies:

* Spring Boot 3.2.2
* jPOS 2.1.7 (for ISO 8583 message handling)
* JSON library 20210307
* Spring Data MongoDB
* Lombok 1.18.30
* SpringDoc OpenAPI UI 2.3.0

All other dependencies will be managed by Maven as defined in the pom.xml file.

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/visa-transaction-simulator.git
   cd visa-transaction-simulator
   ```

2. Build the project using Maven:
   ```
   mvn clean install
   ```

## Configuration

1. Set up the environment variable for the visa.xml file:
   ```
   export VISA_XML_PATH=/path/to/visa.xml
   ```
   Replace `/path/to/visa.xml` with the actual path to your visa.xml file.

   For Windows users, use:
   ```
   set VISA_XML_PATH=C:\path\to\visa.xml
   ```

2. Configure the application properties. Create or update your `application.properties` file with the following content:

   ```properties
   spring.application.name=ISO8583
   server.port=8000
   
   # MongoDB Configuration
   spring.data.mongodb.authentication-database=admin
   spring.data.mongodb.username=root
   spring.data.mongodb.password=root
   spring.data.mongodb.database=isoM
   spring.data.mongodb.port=27017
   spring.data.mongodb.host=localhost
   
   # Swagger/OpenAPI Configuration
   springdoc.api-docs.enabled=true
   springdoc.swagger-ui.enabled=true
   springdoc.api-docs.path=/api-docs
   springdoc.swagger-ui.path=/swagger-ui.html
   springdoc.swagger-ui.operationsSorter=method
   springdoc.swagger-ui.tagsSorter=alpha
   springdoc.swagger-ui.try-it-out-enabled=true
   
   # Logging Configuration
   logging.level.org.springdoc=DEBUG
   logging.level.org.springframework=DEBUG
   ```

   This configuration sets up:
    - The application name and port
    - MongoDB connection details
    - Swagger/OpenAPI documentation settings
    - Logging levels for Spring and SpringDoc

   Make sure to adjust these settings according to your environment, especially the MongoDB credentials and connection details.

3. Ensure your MongoDB server is running and accessible with the configured credentials.

## Accessing API Documentation (Swagger UI)

Once the application is running, you can access the Swagger UI to explore and test the API endpoints. Follow these steps:

1. Ensure the application is running (either via `mvn spring-boot:run` or by executing the JAR file).

2. Open a web browser and navigate to:
   ```
   http://localhost:8000/swagger-ui.html
   ```
   Note: If you've changed the `server.port` in the application properties, replace 8000 with your custom port number.

3. The Swagger UI page will load, displaying all available endpoints grouped by their respective controllers.

4. You can expand each endpoint to see detailed information including:
    - HTTP method (GET, POST, PUT, DELETE, etc.)
    - Request parameters
    - Request body schema (if applicable)
    - Response schemas
    - Available content types

5. To test an endpoint:
    - Click on the endpoint to expand it
    - Click the "Try it out" button
    - Fill in any required parameters or request body
    - Click "Execute" to send the request
    - View the response directly in the Swagger UI

6. You can also access the raw OpenAPI specification in JSON format at:
   ```
   http://localhost:8000/api-docs
   ```

The Swagger UI provides an interactive way to explore and test your API without needing additional tools like Postman or curl.

## Usage

To run the application:

1. Ensure the environment variable for visa.xml is set.
2. Start your MongoDB server.
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
   Or run the generated JAR file:
   ```
   java -jar target/ISO8583-0.0.1-SNAPSHOT.jar
   ```

The application will start on port 8000 as specified in the properties file.

## ISO 8583 Message Types

This simulator supports ISO 8583 message types as implemented by the jPOS library. Common message types include:

- 0100: Authorization Request
- 0110: Authorization Response
- 0400: Reversal Request
- 0410: Response Reversal Response

Refer to the jPOS documentation for more detailed information on message types and their usage.

## Visa XML Packager

The Visa XML packager (visa.xml) is used to format ISO 8583 messages according to Visa's specifications. The application reads this file from the path specified in the `VISA_XML_PATH` environment variable.

In your code, you can access this file path using:
```java
String visaXmlPath = System.getenv("VISA_XML_PATH");
```

Ensure this environment variable is correctly set before running the application.

## Architecture

