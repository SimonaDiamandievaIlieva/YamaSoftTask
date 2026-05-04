# CI/CD Integration Strategy – Test Automation (Roadpass Digital)

## 1. Overview

This document describes the CI/CD integration strategy for the automated TestNG UI test suite. The goal is to ensure fast feedback on every pull request, maintain test stability, and provide clear reporting for engineering teams using CircleCI.

The pipeline integrates:
- TestNG (test execution)
- Maven (build tool)
- CircleCI (CI orchestration)
- ExtentReports (detailed HTML reporting)
- Slack (notifications)

---

## 2. CI Pipeline Strategy

### 2.1 Triggers

The CI pipeline runs on every Pull Request.

Main purpose:
- Validate changes before merge
- Provide fast feedback to developers

Main branch runs are excluded from this pipeline and can later be extended for regression or nightly runs.

---

### 2.2 Test Execution Strategy

The execution is driven by `testng.xml`, which defines a **smoke test suite**.

Only smoke tests are executed in the PR pipeline:
- Focus on critical user flows (e.g. Create Trip, validation scenarios)
- Ensures fast execution and fast feedback
- Reduces CI execution time

---

### 2.3 Parallelization Strategy

Current state:
- Tests run sequentially in a single CircleCI job

Future improvements:
- Introduce CircleCI parallelism (2–4 containers)
- Split tests by:
    - TestNG groups
    - feature modules
    - UI vs API layers

---

## 3. Reporting & Failure Handling

### 3.1 Reporting Layers

#### JUnit (Surefire reports)
Path:
target/surefire-reports

Used for:
- CircleCI test result parsing
- CI dashboards
- historical trends

---

#### ExtentReports
Path:
reports/

Used for:
- detailed HTML reports
- step-by-step execution logs
- screenshots on failure
- debugging test flows visually

---

### 3.2 Artifacts Strategy

Each pipeline stores:

- Surefire reports → CI-level visibility
- ExtentReports → debugging and analysis

This ensures full traceability of test execution.

---

### 3.3 Failure Handling

On test failure:
- CircleCI marks the build as failed
- Slack notification is triggered immediately
- Artifacts are preserved for investigation

Debugging is done via:
- ExtentReports HTML report
- Surefire XML logs
- screenshots

---

### 3.4 PR Feedback

Optional improvements:
- GitHub status checks to block merge on failure
- Slack notifications for success and failure events

---

## 4. Flaky Test Management Strategy

### 4.1 Detection

A test is considered flaky if:
- it fails intermittently without code changes
- it passes on rerun
- inconsistent CI behavior is observed

---

### 4.2 Retry Policy

All tests currently use a controlled retry mechanism at TestNG level (via RetryAnalyzer).

- Each failed test is automatically retried up to 2 time
- Retry is used only for transient UI or environment instability
- Failures are still reported on the first attempt
- Retry attempts are logged for debugging and flakiness analysis

This improves stability without hiding real defects.

---

### 4.3 Ownership

Each flaky test:
- has an assigned owner (QA or Developer)
- is tracked in a ticketing system (e.g. Jira)
- has a resolution SLA of 3–5 days

---

## 5. Metrics for Test Effectiveness

### 5.1 Test Failure Rate
Measures percentage of failed builds over time.

Purpose:
- detects instability in application or tests

---

### 5.2 Flakiness Rate
Measures unstable tests that pass/fail inconsistently.

Purpose:
- improves CI reliability and trust

---

### 5.3 Pipeline Execution Time
Measures time from commit to feedback.

Purpose:
- impacts developer productivity and CI efficiency

---

### 5.4 Smoke Test Coverage
Measures coverage of critical business flows.

Purpose:
- ensures key functionality is always validated in PRs

---

## 6. Summary

This CI/CD strategy provides:
- fast PR feedback using smoke tests
- reliable reporting via JUnit and ExtentReports
- real-time notifications via Slack
- controlled retry mechanism for stability
- structured flaky test management
- measurable CI health metrics

The design is scalable and can evolve into full regression pipelines with parallel execution as the test suite grows.