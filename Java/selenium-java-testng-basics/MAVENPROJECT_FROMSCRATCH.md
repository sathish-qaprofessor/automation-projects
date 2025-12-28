Steps to convert a normal folder to a Maven project:

1. Verify Maven is installed (run in terminal).
```bash
mvn -v
```

2. Add a minimal `pom.xml` at the project root (replace `groupId`/`artifactId`/`version` as needed).
   Explanation: this defines the Maven project and Java version.
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0-SNAPSHOT</version>
  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>
</project>
```

3. Create the standard Maven layout.
   Explanation: move sources into these folders.
```powershell
New-Item -ItemType Directory -Path .\src\main\java,.\src\main\resources,.\src\test\java,.\src\test\resources -Force
```

4. Move your Java code and resources
- Place production code under `src/main/java\{package\}`.
- Place tests under `src/test/java`.
- Update `package` declarations if you changed folder structure.

5. Import as Maven project in IntelliJ IDEA
- Open the project folder in IntelliJ.
- Open `pom.xml` and click `Add as Maven Project` or use `File > New > Project from Existing Sources...` and choose Maven import.
- Enable auto-import if prompted.

6. Build with Maven to resolve dependencies and verify
```bash
mvn clean package
```

That converts the folder to a Maven project.