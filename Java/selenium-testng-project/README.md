# Selenium TestNG Project — README (Updated)

This README documents how to set up, configure and run tests for this Selenium + TestNG automation project. It presents the project structure details and provides exact commands to execute tests using `testng.xml`.

---

## Quick checklist
- Java 11+ installed
- Maven 3.6+ installed
- IDE (IntelliJ recommended) configured for the project
- Browsers installed (Chrome/Firefox)
- Project is located at the workspace root

---

## Correct project structure

This project follows a standard Maven layout. The important folders are:

```
selenium-testng-project/
├── pom.xml
├── testng.xml                 # TestNG suite file (runs all tests)
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/automation/pages/    # Page objects
│   │   └── resources/                   # config files, drivers
│   └── test/
│       └── java/
│           └── org/automation/tests/    # TestNG test classes
├── downloads/
├── screenshots/

```

Note: In your repository the page objects currently live under `src/main/java/org/automation/pages` and test classes live under `src/test/java/org/automation/tests`.

---

## Maven dependencies

The project uses Maven to manage libraries. Below are the dependencies currently declared in the project's `pom.xml`. Copy these into your `pom.xml` if you need to recreate them.

```xml
<dependencies>
    <!-- Framework artifact (project-local or hosted) -->
    <dependency>
        <groupId>org.framework</groupId>
        <artifactId>testng-selenium-framework</artifactId>
        <version>1.3-SNAPSHOT</version>
    </dependency>

    <!-- Selenium WebDriver -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.19.0</version>
    </dependency>

    <!-- TestNG -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.8.0</version>
    </dependency>

    <!-- WebDriverManager for automatic driver binaries -->
    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>6.1.0</version>
    </dependency>

    <!-- ExtentReports for reporting (optional) -->
    <dependency>
        <groupId>com.aventstack</groupId>
        <artifactId>extentreports</artifactId>
        <version>5.1.2</version>
    </dependency>
</dependencies>
```

Install / refresh dependencies:

```powershell
# Download dependencies and build
mvn clean install
# Or just resolve dependencies
mvn dependency:resolve
```

---

## Running tests using `testng.xml`

The repository already contains a `testng.xml` at the project root that lists all test classes. Use one of the following ways to execute the suite.

1) Run with Maven (recommended)

- To run the suite declared in `testng.xml` via Maven Surefire, add the following plugin configuration in your `pom.xml` if not present (example):

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.0.0</version>
      <configuration>
        <suiteXmlFiles>
          <suiteXmlFile>testng.xml</suiteXmlFile>
        </suiteXmlFiles>
      </configuration>
    </plugin>
  </plugins>
</build>
```

- Then run the full suite from project root:

```powershell
mvn clean test
```

This will run the classes declared in `testng.xml`.

2) Run TestNG directly using the TestNG runner in the IDE

- In IntelliJ: Right-click `testng.xml` and choose Run. It will execute the suite defined in the file.

3) Run a single TestNG class with Maven

- To run one test class (for faster iteration):

```powershell
mvn -Dtest=org.automation.tests.SignInTests test
```

(You may also use the class simple name: `-Dtest=SignInTests`)

4) Run TestNG via command line (if you prefer the TestNG jar)

- Use TestNG CLI to run the suite file (requires TestNG on classpath). This is less common when using Maven.

---

## Notes about `testng.xml` in this repo

- The current `testng.xml` includes these classes:
  - `org.automation.tests.SignInTests`
  - `org.automation.tests.ContactUsTests`
  - `org.automation.tests.StoreHomeTests`

- If you add or remove test classes, update `testng.xml` accordingly, or use Maven's `-Dtest` filter to include patterns.

---
