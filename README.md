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

1. Open a Windows command prompt and run the full test suite for the project:

```cmd
cd /d "C:\Users\tewod\eclipse-workspace\java-test"
mvn clean test
```

2. To run a specific module or to pass an API key (some environments require an `x-api-key`):

- Pass API key via system property:

```cmd
mvn -f "C:\Users\tewod\eclipse-workspace\test-project\pom.xml" -Dreqres.api.key=YOUR_KEY clean test
```

- Or export an environment variable for the current command:

```cmd
set "REQRES_API_KEY=YOUR_KEY" && mvn -f "C:\Users\tewod\eclipse-workspace\test-project\pom.xml" clean test
```

3. Run only the `java-test` module (already the current folder if you `cd` into it):

```cmd
mvn -f "C:\Users\tewod\eclipse-workspace\java-test\pom.xml" test
```

## Configuration

The test suite supports the following environment variables and system properties:

- `REQRES_BASE_URI` or `-Dreqres.baseUri` — override the base URI for requests (default: `https://reqres.in`). Use this to point tests to a local WireMock instance (e.g., `http://localhost:8089`).
- `REQRES_API_KEY` or `-Dreqres.api.key` — when populated, tests will send this value in the `x-api-key` header for requests that require an API key.
- `USE_WIREMOCK` or `-DuseWireMock` — the `java-test` suite includes `WireMockHooks` which default to enabling WireMock; set this to `false` to disable starting a local stub server.

Example: run with WireMock disabled and a custom base URI:

```cmd
mvn -f "C:\Users\tewod\eclipse-workspace\java-test\pom.xml" -DuseWireMock=false -Dreqres.baseUri=https://reqres.in clean test
```

## WireMock (local stubbing)

The `java-test` project starts a WireMock server in the test hooks by default. WireMock stubs are defined in `WireMockHooks.java` and provide deterministic responses for the feature scenarios.

If you prefer to run the tests against the real `reqres.in` service, disable WireMock via `-DuseWireMock=false`.

## Project layout (important files)

- `pom.xml` — Maven configuration and test dependencies
- `src/test/java/com/reqres/api/hooks/WireMockHooks.java` — WireMock setup and stubs
- `src/test/java/com/reqres/api/services/UserService.java` — Rest-Assured wrappers for HTTP calls
- `src/test/java/com/reqres/api/steps/UserSteps.java` — Cucumber step definitions
- `src/test/resources/features/*.feature` — Cucumber feature files

## Test reports and logs

- Surefire reports: `target/surefire-reports`
- Cucumber HTML report (if generated): `target/cucumber-html-report`
- JSON report: `target/cucumber.json`

## Troubleshooting

- If tests fail with `401 Unauthorized` and response `{"error":"Missing API key"}`, provide `REQRES_API_KEY` or `-Dreqres.api.key` when running the tests.
- If WireMock is started but you still receive unexpected responses, ensure no other process is occupying the configured port (default `8089`). You can change the port in `WireMockHooks.java`.
- If logging is silent, SLF4J may be using a NOP binder in the test classpath. Add a test-scoped SLF4J binding (for example `slf4j-simple`) if you want console logs during test runs.

## Contributing

- Open issues and PRs on the GitHub repository.
- Keep tests deterministic: prefer local WireMock stubs for CI or mock external dependencies.

## License

This repository is provided as-is for demonstration and learning purposes. Add a license file if you intend to publish or share it publicly.

---

If you'd like, I can also:
- Commit and push this updated `README.md` (I can run the git commands for you), or
- Add a short CONTRIBUTING.md, LICENSE, and a `.gitattributes` file. Just tell me which to add next.

## Repository

GitHub: https://github.com/teddy004/reqres-java-rest-assured-api-tests