# selenium-java-testng-basics

This Maven project demonstrates Selenium WebDriver automation with TestNG and Java. It covers locator strategies, browser automation, waits, file uploads/downloads, and TestNG features.

## Project Structure

- `src/test/java/org/basics/` — Test source code
  - `drivers/` — WebDriver setup and browser utilities
  - `locators/` — Locator strategy test classes (Id, Name, ClassName, TagName, LinkText, PartialLinkText, CssSelector, XPath)
  - `selenium/` — Selenium feature tests (waits, actions, file upload/download, etc.)
  - `testng/` — TestNG feature tests (groups, order, annotations)
  - `testngimpl/` — TestNG parameterized and advanced tests
- `drivers/` — Browser driver executables (chromedriver, geckodriver, msedgedriver)
- `fileuploads/` — Sample files for upload tests
- `screenshots/` — Output screenshots (if any)
- `downloads/` — Downloaded files (if any)
- `testngfiles/` — TestNG suite files (XML and YAML)
- `pom.xml` — Maven project configuration

## Prerequisites

- Java 17+
- Maven 3.6+
- Chrome/Firefox/Edge browsers (for local runs)

## How to Run Tests

### 1. Run All Tests

```
mvn clean test
```

### 2. Run a Specific Test Class

```
mvn -Dtest=org.basics.locators.IdElementLocatorStrategy test
```

### 3. Run a Specific Test Method in a Class

```
mvn -Dtest=org.basics.locators.IdElementLocatorStrategy#idStrategy test
```

### 4. Run Tests Using TestNG XML Suite

```
mvn test -DsuiteXmlFile=testngfiles/testng-methods.xml
```

### 5. Run Tests Using TestNG YAML Suite (TestNG 7.11.0+)

```
mvn test -DsuiteXmlFile=testngfiles/test-methods.yaml
```

## Notes

- WebDriverManager is used for automatic driver management.
- Test reports are generated in `target/surefire-reports/`.
- Sample files for upload/download are in `fileuploads/`.
- Update or add TestNG suite files in `testngfiles/` as needed.

## Example Test Classes

- `org.basics.locators.IdElementLocatorStrategy` — Demonstrates locating elements by ID.
- `org.basics.selenium.ExplicitWaitsTest` — Shows explicit wait usage.
- `org.basics.selenium.FileUploadsTest` — Demonstrates file upload automation.

---

For more details, see the source code and suite files.
