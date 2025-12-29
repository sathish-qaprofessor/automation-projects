# RestAssured Ext API Project

## Project Overview
This project is a Java-based API automation framework for testing RESTful APIs. It leverages RestAssured for HTTP requests, TestNG for test orchestration, and ExtentReports for reporting. The framework is modular, extensible, and supports schema validation, configuration management, and logging.

## Project Structure
```
restassured-ext-api-project/
├── pom.xml
├── automation-extent-report.html
├── logs/                        # Execution logs for each test run
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/catsapi/
│   │   │       ├── endpoints/   # API endpoint definitions
│   │   │       └── models/      # POJOs for API responses
│   │   └── resources/
│   │       ├── catsapi-schemas/ # JSON schemas for Cat API
│   │       └── jokesapi-schemas/# JSON schemas for Joke API
│   └── test/
│       ├── java/
│       │   ├── org/catsapi/tests/ # Test classes (e.g., CatsApiTests.java)
│       │   └── org/jokesapi.tests # Test classes (e.g., JokessApiTests.java) 
│       └── resources/
│           └── config/            # Environment configs (dev, qa, uat, etc.)
└── target/                      # Maven build output
```

## Key Features
- **REST API Testing**: Automated tests for Jokes & Cat API endpoints (breeds, images, etc.)
- **Schema Validation**: Validates API responses against JSON schemas
- **Configurable Environments**: Supports multiple environments via properties files
- **Logging**: Detailed logs for each test execution
- **Reporting**: ExtentReports integration for rich HTML reports
- **Reusable Service Layer**: Abstracted HTTP request logic for easy test writing

## Maven Dependencies
All dependencies are managed in `pom.xml`. Here is the full structure of the dependencies section:

```xml
<dependencies>
    <dependency>
        <groupId>org.framework</groupId>
        <artifactId>java-api-framework</artifactId>
        <version>1.1-SNAPSHOT</version>
    </dependency>
    <!-- TestNG (required for running tests) -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.11.0</version>
        <scope>test</scope>
    </dependency>
    <!-- ExtentReports (required if you enable extent reporting) -->
    <dependency>
        <groupId>com.aventstack</groupId>
        <artifactId>extentreports</artifactId>
        <version>5.1.1</version>
    </dependency>
    <!-- REST Assured (core) -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
    </dependency>
    <!-- REST Assured JSON Path -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>json-path</artifactId>
        <version>5.4.0</version>
    </dependency>
    <!-- REST Assured JSON Schema Validator -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>json-schema-validator</artifactId>
        <version>5.3.0</version>
    </dependency>
    <!-- Jackson Databind (for JSON serialization/deserialization) -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.15.2</version>
    </dependency>
    <!-- Lombok (if you use Lombok annotations in your models) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

## How to Run Tests
1. **Install dependencies:**
   ```sh
   mvn clean install
   ```
2. **Run all tests:**
   ```sh
   mvn test
   ```
3. **Run a specific test class:**
   ```sh
   mvn -Dtest=org.catsapi.tests.CatsApiTests test
   ```

## Configuration
- Environment-specific properties are in `src/test/resources/config/` (e.g., `dev-config.properties`, `qa-config.properties`).
- JSON schemas for response validation are in `src/main/resources/catsapi-schemas/` and `src/main/resources/jokesapi-schemas/`.

## Adding/Editing Tests
- Add new test classes in `src/test/java/org/catsapi/tests/`.
- Add or update POJOs in `src/main/java/org/catsapi/models/`.
- Update endpoint constants in `src/main/java/org/catsapi/endpoints/CatsApiEndpoints.java`.
- Add new JSON schemas in the appropriate schemas directory.

## Reports & Logs
- HTML reports are generated as `automation-extent-report.html` in the project root.
- Execution logs are stored in the `logs/` directory, with a separate log file for each test run.

## Author & License
- Author: Sathish
- License: MIT (or your preferred license)
