# Allure Report Guide - End-to-End Setup

**Author:** Quang.Phan (Vincent)  
**Last Updated:** October 19, 2025  
**Project:** Playwright Java Test Automation Framework

---

## Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Libraries & Dependencies](#libraries--dependencies)
4. [Project Structure](#project-structure)
5. [Configuration Setup](#configuration-setup)
6. [How the Compiler Works](#how-the-compiler-works)
7. [Running Tests](#running-tests)
8. [Generating Reports](#generating-reports)
9. [Viewing Reports](#viewing-reports)
10. [Report Structure](#report-structure)
11. [Troubleshooting](#troubleshooting)
12. [Best Practices](#best-practices)

---

## Overview

This guide explains how to set up and use Allure Reports in a Playwright Java test automation framework. Allure is a lightweight, flexible, and informative test report tool that provides detailed information about test execution.

### What is Allure?

Allure is a flexible lightweight multi-language test report tool that not only shows what have been tested, but allows everyone participating in the development process to extract maximum of useful information from everyday execution of tests.

**Key Features:**
- ✅ Beautiful HTML reports
- ✅ Test history and trends
- ✅ Test categorization (Epic, Feature, Story)
- ✅ Environment information
- ✅ Test attachments (screenshots, logs)
- ✅ Timeline view
- ✅ Severity levels

---

## Prerequisites

### System Requirements

| Component | Version | Notes |
|-----------|---------|-------|
| Java | 21 LTS | Configured in pom.xml |
| Maven | 3.6.0+ | For building and running tests |
| Git | Latest | For version control |
| Browser | Chrome/Chromium | For Playwright automation |

### Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Install Maven (if not installed)
# macOS
brew install maven

# Linux
sudo apt-get install maven

# Windows
choco install maven
```

---

## Libraries & Dependencies

### Core Dependencies

#### 1. **Playwright** (Browser Automation)
```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.46.0</version>
</dependency>
```
**Purpose:** Automates browser interactions for testing  
**Handles:** Chrome, Firefox, Safari, Edge browsers

#### 2. **TestNG** (Test Framework)
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.2</version>
    <scope>test</scope>
</dependency>
```
**Purpose:** Test execution framework  
**Handles:** Test organization, execution, assertions

#### 3. **Allure TestNG** (Report Integration)
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.24.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-java-commons</artifactId>
    <version>2.24.0</version>
    <scope>compile</scope>
</dependency>
```
**Purpose:** Integrates Allure with TestNG  
**Handles:** Test result collection, annotations

#### 4. **Logging** (Log4j + SLF4J)
```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.22.0</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.22.0</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.22.0</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.12</version>
</dependency>
```
**Purpose:** Logging framework  
**Handles:** Test logs, debug information

#### 5. **Lombok** (Code Generation)
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>
```
**Purpose:** Reduces boilerplate code  
**Handles:** Getters, setters, constructors

#### 6. **Apache Commons IO** (File Operations)
```xml
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.15.0</version>
</dependency>
```
**Purpose:** File and directory utilities  
**Handles:** Report file copying, directory management

#### 7. **AspectJ** (AOP Framework)
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.23</version>
</dependency>
```
**Purpose:** Aspect-oriented programming  
**Handles:** Test interception, method wrapping

### Maven Plugins

#### 1. **Maven Compiler Plugin**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.14.0</version>
    <configuration>
        <source>21</source>
        <target>21</target>
        <release>21</release>
    </configuration>
</plugin>
```
**Purpose:** Compiles Java source code  
**Handles:** Java 21 compatibility

#### 2. **Maven Surefire Plugin**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
        </suiteXmlFiles>
        <argLine>
            --add-opens java.base/java.util=ALL-UNNAMED
            --add-opens java.base/java.lang=ALL-UNNAMED
            -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/1.9.23/aspectjweaver-1.9.23.jar"
        </argLine>
        <systemPropertyVariables>
            <allure.results.directory>${project.build.directory}/allure-results</allure.results.directory>
        </systemPropertyVariables>
    </configuration>
</plugin>
```
**Purpose:** Runs tests during Maven build  
**Handles:** Test execution, result collection

#### 3. **Allure Maven Plugin** ⭐
```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.14.0</version>
    <configuration>
        <reportVersion>2.24.0</reportVersion>
        <reportDirectory>${project.build.directory}/allure-report</reportDirectory>
        <resultsDirectory>${project.build.directory}/allure-results</resultsDirectory>
        <singleFile>true</singleFile>
    </configuration>
</plugin>
```
**Purpose:** Generates Allure HTML reports  
**Handles:** Report generation from test results

---

## Project Structure

```
Playwright_java_demo/
├── src/
│   ├── main/java/org/example/hrmOrange/
│   │   ├── allure/
│   │   │   └── AllureReportUtils.java          # Report generation logic
│   │   ├── helpers/
│   │   │   └── ConfigReader.java               # Configuration management
│   │   ├── managers/
│   │   │   ├── BrowserFactory.java             # Browser creation
│   │   │   └── PageManager.java                # Page management
│   │   └── constants/
│   │       └── AppConfig.java                  # App configuration
│   │
│   └── test/
│       ├── java/org/example/hrmOrange/
│       │   ├── common/
│       │   │   └── BaseTest.java               # Base test class
│       │   └── testcase/ui/login/
│       │       └── OrangeHRM_Login_TC*.java    # Test cases
│       │
│       └── resources/config/
│           └── config.properties               # Test configuration
│
├── target/
│   ├── allure-results/                         # Test results (JSON)
│   │   ├── *.json                              # Test result files
│   │   └── environment.properties              # Environment info
│   │
│   ├── allure-report/                          # Generated HTML report
│   │   ├── index.html                          # Main report
│   │   ├── css/                                # Styling
│   │   └── images/                             # Icons
│   │
│   └── classes/                                # Compiled classes
│
├── reports/                                    # Final exported reports
│   └── 2025-10-19/
│       ├── index.html                          # Navigation page
│       └── common_145510/
│           ├── index.html                      # Allure report
│           ├── css/
│           └── images/
│
├── pom.xml                                     # Maven configuration
├── testng.xml                                  # TestNG configuration
└── ALLURE-REPORT-GUIDE.md                      # This file
```

---

## Configuration Setup

### 1. pom.xml Configuration

**Key Properties:**
```xml
<properties>
    <java.version>21</java.version>
    <playwright.version>1.46.0</playwright.version>
    <testng.version>7.10.2</testng.version>
    <allure.version>2.24.0</allure.version>
    <aspectj.version>1.9.23</aspectj.version>
    <allure-maven-plugin.version>2.14.0</allure-maven-plugin.version>
</properties>
```

### 2. testng.xml Configuration

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.1.dtd">
<suite name="Playwright Test Suite" verbose="1" parallel="tests" thread-count="1">
    <parameter name="browser" value="chromium"/>
    <parameter name="headless" value="false"/>
    <parameter name="baseUrl" value="https://opensource-demo.orangehrmlive.com"/>

    <test name="Login Tests">
        <packages>
            <package name="org.example.hrmOrange.testcase.ui.login"/>
        </packages>
    </test>
</suite>
```

### 3. config.properties Configuration

```properties
# Browser Configuration
BROWSER=chromium
HEADLESS=false
VIEWPORT_WIDTH=1920
VIEWPORT_HEIGHT=1080

# Application Configuration
BASE_URL=https://opensource-demo.orangehrmlive.com
TIMEOUT_PAGE_LOAD=30000

# Report Configuration
EXTENT_REPORT_PATH=reports/
```

### 4. log4j2.xml Configuration

Create `src/test/resources/log4j2.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        <File name="File" fileName="target/test.log">
            <PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="File"/>
        </Root>
    </Loggers>
</Configuration>
```

---

## How the Compiler Works

### Maven Build Lifecycle

```
mvn clean test
    ↓
1. CLEAN PHASE
   └─ Deletes target/ directory
   
2. COMPILE PHASE
   ├─ Compiles src/main/java → target/classes/
   └─ Compiles src/test/java → target/test-classes/
   
3. TEST PHASE
   ├─ Runs Maven Surefire Plugin
   ├─ Executes testng.xml
   ├─ Runs all test cases
   ├─ Collects results → target/allure-results/
   └─ Generates environment.properties
   
4. REPORT GENERATION
   ├─ Runs mvn allure:report
   ├─ Processes JSON results
   ├─ Generates HTML → target/allure-report/
   └─ Copies to reports/ folder
```

### Detailed Compilation Steps

#### Step 1: Source Code Compilation
```bash
mvn compile
```
- Compiles Java source files
- Generates bytecode (.class files)
- Validates syntax
- Resolves dependencies

#### Step 2: Test Compilation
```bash
mvn test-compile
```
- Compiles test source files
- Includes main classes in classpath
- Prepares test environment

#### Step 3: Test Execution
```bash
mvn test
```
- Runs Surefire plugin
- Executes TestNG suite
- Collects test results
- Generates Allure JSON files

#### Step 4: Report Generation
```bash
mvn allure:report
```
- Processes Allure JSON results
- Generates HTML report
- Creates interactive dashboard

### AspectJ Weaving

AspectJ is used for runtime instrumentation:

```bash
-javaagent:${settings.localRepository}/org/aspectj/aspectjweaver/1.9.23/aspectjweaver-1.9.23.jar
```

**What it does:**
- Intercepts test methods
- Captures test execution data
- Integrates with Allure listeners
- Records test steps and attachments

---

## Running Tests

### Option 1: Run All Tests

```bash
# Clean build and run all tests
mvn clean test

# Output:
# [INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

### Option 2: Run Specific Test Class

```bash
mvn test -Dtest=OrangeHRM_Login_TC1_VerifyThatUserCanLoginWithValidDataSuccessfully
```

### Option 3: Run Tests with Specific Pattern

```bash
mvn test -Dtest=OrangeHRM_Login_TC*
```

### Option 4: Skip Tests During Build

```bash
mvn clean install -DskipTests
```

### Option 5: Run from IDE (IntelliJ IDEA)

1. Right-click on `testng.xml`
2. Select "Run 'testng.xml'"
3. Tests execute in IDE console

### Test Execution Output

```
[14:55:07] [INFO] org.example.hrmOrange.testcase.ui.login.OrangeHRM_Login_TC1 - Step 1: Navigate to login page
[14:55:10] [INFO] org.example.hrmOrange.testcase.ui.login.OrangeHRM_Login_TC1 - Step 2: Verify successful login
[14:55:11] [INFO] org.example.hrmOrange.allure.AllureReportUtils - Starting Allure report generation...
[14:55:13] [INFO] org.example.hrmOrange.allure.AllureReportUtils - ✅ Allure Report generated successfully
```

---

## Generating Reports

### Automatic Report Generation

Reports are generated automatically after tests complete:

```bash
mvn clean test
```

**Process:**
1. Tests execute
2. Results saved to `target/allure-results/`
3. `mvn allure:report` runs automatically
4. Report generated in `target/allure-report/`
5. Report copied to `reports/yyyy-MM-dd/`

### Manual Report Generation

```bash
# Generate report from existing results
mvn allure:report

# Serve report in browser
mvn allure:serve
```

### Report Generation Flow

```
Test Execution
    ↓
Results: target/allure-results/
├── *.json (Test results)
├── *-attachment.txt (Logs)
└── environment.properties (Environment info)
    ↓
Maven Allure Plugin
    ↓
Report: target/allure-report/
├── index.html (3.1 MB)
├── css/ (Styling)
└── images/ (Icons)
    ↓
Export: reports/2025-10-19/common_145510/
└── Final report for archiving
```

---

## Viewing Reports

### Option 1: Open HTML File Directly

```bash
# macOS
open reports/2025-10-19/common_145510/index.html

# Linux
xdg-open reports/2025-10-19/common_145510/index.html

# Windows
start reports/2025-10-19/common_145510/index.html
```

### Option 2: Use File URL

```
file:///Users/vincent/Project/uat/Playwright_java_demo/reports/2025-10-19/common_145510/index.html
```

### Option 3: Serve with Maven

```bash
mvn allure:serve
```
- Starts local server
- Opens report in browser
- Default: http://localhost:4040

### Option 4: Simple HTTP Server

```bash
cd reports/2025-10-19/common_145510/
python3 -m http.server 8000

# Access: http://localhost:8000
```

---

## Report Structure

### Report Dashboard

The main report page shows:

1. **Summary Statistics**
   - Total tests run
   - Pass/Fail counts
   - Pass rate percentage
   - Execution time

2. **Test Results**
   - Test name
   - Status (Passed/Failed)
   - Execution time
   - Severity level

3. **Categories**
   - By Epic
   - By Feature
   - By Story
   - By Severity

4. **Timeline**
   - Test execution order
   - Duration of each test
   - Parallel execution info

5. **Environment**
   - Browser Version: chromium (Playwright)
   - Host Name: local
   - Local OS: 

### Report Files

```
index.html (3.1 MB)
├── app.js (Application logic)
├── styles.css (Styling)
├── css/ (Additional styles)
│   ├── app.css
│   ├── fonts.css
│   └── styles.css
├── images/ (Icons and logos)
│   ├── favicon.ico
│   ├── logo.svg
│   └── ...
└── data/ (Test data)
    ├── test-cases/
    ├── attachments/
    └── history/
```

---

## Troubleshooting

### Issue 1: Report Not Generated

**Symptom:** `target/allure-report/` is empty

**Solution:**
```bash
# Ensure test results exist
ls target/allure-results/

# Manually generate report
mvn allure:report

# Check for errors
mvn allure:report -X
```

### Issue 2: AspectJ Weaving Error

**Symptom:** `Unsupported class file major version 68`

**Solution:**
```xml
<!-- Update AspectJ version in pom.xml -->
<aspectj.version>1.9.23</aspectj.version>
```

### Issue 3: Tests Not Running

**Symptom:** `Tests run: 0`

**Solution:**
```bash
# Verify testng.xml is correct
cat testng.xml

# Check test class names match pattern
mvn test -Dtest=*Test*

# Run specific test
mvn test -Dtest=OrangeHRM_Login_TC1*
```

### Issue 4: Browser Not Found

**Symptom:** `Playwright browsers not installed`

**Solution:**
```bash
# Install Playwright browsers
mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"
```

### Issue 5: Report Shows No Data

**Symptom:** Report generated but no test data visible

**Solution:**
```bash
# Verify environment.properties
cat target/allure-results/environment.properties

# Check test results JSON
ls -la target/allure-results/*.json

# Regenerate report
rm -rf target/allure-report/
mvn allure:report
```

---

## Best Practices

### 1. Test Organization

```java
@Epic("Authentication")
@Feature("Login Functionality")
public class LoginTests extends BaseTest {
    
    @Test
    @Story("Successful login with valid credentials")
    @Description("User can login with valid username and password")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        // Test implementation
    }
}
```

### 2. Adding Test Steps

```java
@Step("Navigate to login page")
public void navigateToLoginPage() {
    page.navigate(AppConfig.URL);
}

@Step("Enter username: {username}")
public void enterUsername(String username) {
    page.fill("input[name='username']", username);
}
```

### 3. Adding Attachments

```java
@Step("Take screenshot")
public void takeScreenshot() {
    byte[] screenshot = page.screenshot();
    Allure.addAttachment("Screenshot", "image/png", 
        new ByteArrayInputStream(screenshot), ".png");
}
```

### 4. Adding Logs

```java
@Step("Verify login success")
public void verifyLoginSuccess() {
    logger.info("Checking dashboard title");
    String title = page.title();
    Allure.addAttachment("Page Title", title);
    assert title.contains("Dashboard");
}
```

### 5. Environment Configuration

```java
// In BaseTest.java
@BeforeMethod
public void setUp() {
    BrowserFactory browserFactory = new BrowserFactory();
    browserFactory.createBrowser(AppConfig.BROWSER);
    
    // Set browser version for report
    String browserVersion = AppConfig.BROWSER + " (Playwright)";
    ConfigReader.BROWSER_VERSION = browserVersion;
    
    PageManager.getPage().navigate(AppConfig.URL);
}
```

### 6. Report Export

```java
// In AllureReportUtils.java
public static void generateAllureReport(String suiteName) {
    try {
        writeEnvironment();
        createDirectoryIfNotExist(REPORT_DIR);
        if (runAllureCommand("mvn -q --no-transfer-progress allure:report")) {
            moveReportToTarget(suiteName);
            logger.info("✅ Allure Report generated successfully");
        }
    } catch (Exception e) {
        logger.error("Error generating Allure Report: " + e.getMessage(), e);
    }
}
```

### 7. CI/CD Integration

```bash
#!/bin/bash
# ci-pipeline.sh

# Run tests
mvn clean test

# Check exit code
if [ $? -eq 0 ]; then
    echo "Tests passed!"
    
    # Generate report
    mvn allure:report
    
    # Archive report
    tar -czf allure-report-$(date +%Y%m%d-%H%M%S).tar.gz reports/
else
    echo "Tests failed!"
    exit 1
fi
```

### 8. Report Retention

```bash
# Keep last 30 days of reports
find reports/ -type d -mtime +30 -exec rm -rf {} \;

# Archive old reports
tar -czf reports-archive-$(date +%Y%m%d).tar.gz reports/
```

---

## Quick Reference Commands

```bash
# Clean and run all tests
mvn clean test

# Run specific test
mvn test -Dtest=OrangeHRM_Login_TC1*

# Generate report only
mvn allure:report

# Serve report in browser
mvn allure:serve

# Skip tests during build
mvn clean install -DskipTests

# View test results
cat target/allure-results/environment.properties

# List all test result files
ls -la target/allure-results/

# Open report in browser
open reports/2025-10-19/common_145510/index.html

# Clean all reports
rm -rf target/allure-* reports/
```

---

## Support & Documentation

- **Allure Official:** https://docs.qameta.io/allure/
- **Playwright Java:** https://playwright.dev/java/
- **TestNG:** https://testng.org/
- **Maven:** https://maven.apache.org/

---

## Version History

| Date | Version | Changes |
|------|---------|---------|
| 2025-10-19 | 1.0 | Initial comprehensive guide |

---

**Created by:** Quang.Phan (Vincent) 

**Contributor:**

**Last Modified:** 2025-10-19  
**Status:** ✅ Complete & Verified
