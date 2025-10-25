# reqres-java-rest-assured-api-tests

Automated API test suite for the ReqRes public API (https://reqres.in) implemented in Java using:

- Rest-Assured (HTTP assertions)
- Cucumber (BDD feature scenarios)
- TestNG (test runner)
- WireMock (local stubbing for reliable test runs)

This repository contains test-only code (no production/main sources). The suite is intended for local development, CI pipelines, and demonstration of API automation patterns.

## Quick start (Windows / cmd.exe)

Prerequisites:
- Java 11 (or matching configured Maven compiler target)
- Maven 3.6+
- Git (if cloning/pushing)

## Test reports and logs

- Surefire reports: `target/surefire-reports`
- Cucumber HTML report (if generated): `target/cucumber-html-report`
- JSON report: `target/cucumber.json`

 
## Repository

GitHub: https://github.com/teddy004/reqres-java-rest-assured-api-tests