# Automation Framework (UI - Selenium + TestNG)

This is a UI test automation framework built with Java, Selenium WebDriver, TestNG, and Maven.  
The framework follows the Page Object Model (POM) design pattern and includes reporting, retry logic, and CI integration.

---

## 🛠 Built With

- Maven - Dependency management and build tool
- TestNG 7.10.2 - Test execution framework
- Java 11 - Programming language
- Selenium 4.28.1 - UI automation framework
- WebDriverManager 5.6.3 - Driver management
- ExtentReports 5.1.2 - Reporting
- JavaFaker 1.0.2 - Test data generation

---

## 🧪 Framework Structure

### core
Contains core framework utilities:

- PropertiesManager → loads configuration from config.properties
- SetUpWebDriver → handles WebDriver initialization (ThreadLocal, multi-browser)
- UserActions → reusable Selenium actions (click, type, waits, JavaScript handling)
- Utils → helper methods for configuration, driver, and timeout
- ScreenshotUtil → captures screenshots on test failure

---

### pages
Page Object Model implementation:

- HomePage → homepage actions and popup handling
- RegistrationPage → user registration flow
- WhereAreYouGoingPage → trip creation form and date selection
- TripBuilderPage → trip building and waypoint handling
- AutoSuggestHelper → reusable autocomplete dropdown handler

---

### testComponents
Test framework layer:

- BaseTest → setup and teardown, driver initialization, test data setup
- TestListeners → ExtentReports integration and screenshot capture on failure
- ExtentManager → report initialization
- Retry → retry mechanism for failed tests (max 2 retries)

---

### tests
Test cases:

- CreateNewTripTests → positive scenarios for trip creation
- CreateTripValidationTests → validation and negative scenarios

---

## ⚙️ Configuration

Configuration file:

src/test/resources/config.properties

Example:

browser=chrome  
timeoutSeconds=5  
base.url=https://maps.roadtrippers.com

You can override properties via command line:

-Dbrowser=firefox  
-DtimeoutSeconds=10

---

## 🧪 Test Groups

The framework supports TestNG groups for flexible execution.

### Available groups:
- smoke → critical test scenarios

---

## ▶️ How to Run Tests

### Run smoke tests

mvn clean test -Dgroups=smoke


---

### Run all tests

mvn clean test

---

## 📊 Reporting

ExtentReports is generated after execution:

/reports/ExtentReport_<timestamp>.html

Includes:
- Test execution status (PASS / FAIL / SKIP)
- Error details and stack traces
- Screenshots on failure

---

## 🔁 Retry Logic

Failed tests are automatically retried up to 2 times using TestNG RetryAnalyzer.

---

## 🖥 WebDriver Management

- ThreadLocal WebDriver (thread-safe execution)
- WebDriverManager (automatic driver setup)
- Cross-browser support (Chrome, Firefox, Edge)

---

## ☁️ CI Integration

The project includes a sample CircleCI configuration file located in:

.circleci/config.yml

This configuration demonstrates how the test suite can be integrated into a CI pipeline.

The pipeline is designed to:
- Execute Maven build and tests
- Run automated UI tests
- Generate test results and reports
- Store execution artifacts

This setup can be used as a baseline for integrating the framework into a real CI environment and running tests on every Pull Request.

---

## 🧠 Key Features

- Page Object Model (POM)
- Reusable action layer (UserActions)
- Auto-suggest dropdown handling
- Date picker automation
- Popup and iframe handling
- Screenshot capture on failure
- Retry mechanism for flaky tests
- CI-ready architecture

---

## 📌 Notes

- Framework is designed for UI automation of a trip planning flow
- Supports scalable and maintainable test development
- Easily extendable with new test scenarios