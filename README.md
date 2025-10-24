java-test
=========

Overview
--------
Small Maven project that contains an Appium test helper. Key points:
- Java 11 (maven.compiler.source/target = 11)
- Selenium Java 3.141.59
- Appium Java Client 7.6.0
- A sample test class lives at `src/test/java/java_test/java_test/SimpleAppiumTest.java`. That class currently uses a `main()` method and starts an AndroidDriver directly (not a JUnit/TestNG test).

ReqRes API automation
---------------------
This project also contains a small API automation framework (TestNG + Cucumber + Rest Assured) that targets the public ReqRes API documented at:

https://reqres.in/api-docs/#/

Key test files (under `src/test`)
- Feature: `src/test/resources/features/user_api.feature`
- Steps: `src/test/java/com/reqres/api/steps/UserSteps.java`
- Runner: `src/test/java/com/reqres/api/runners/TestRunnerTest.java`
- Services: `src/test/java/com/reqres/api/services/UserService.java`
- Models: `src/test/java/com/reqres/api/models/*`
- Request spec: `src/test/java/com/reqres/api/config/RequestManager.java`

Quick run (Windows cmd.exe)
--------------------------
Run the Cucumber TestNG suite (this will attempt to call https://reqres.in per the official API docs):

```cmd
cd /d C:\Users\tewod\eclipse-workspace\java-test
mvn clean test
```

View reports
------------
- Cucumber HTML: `target/cucumber-html-report/index.html`
- Cucumber JSON: `target/cucumber.json`
- TestNG / Surefire reports: `target/surefire-reports/`

Connectivity notes (important)
-----------------------------
ReqRes is a public API and does not require an API key for the endpoints used by these tests. The framework will not send an API key by default.

If you experience network errors or receive 401/403 responses, these are typically caused by local network/proxy restrictions or DNS overrides. In that case, try one of the following:

1) Run behind a network that allows direct outbound HTTPS (no corporate interception).
2) Configure Maven/Java to use your company proxy (Windows cmd example):

```cmd
mvn -Dhttps.proxyHost=proxy.company.com -Dhttps.proxyPort=8080 clean test
```

or set environment variables (cmd.exe):

```cmd
set HTTPS_PROXY=http://proxy.company.com:8080
set HTTP_PROXY=http://proxy.company.com:8080
mvn clean test
```

Repository
----------
Add your repository URL here (GitHub or GitLab):

- Repository: https://github.com/your-username/your-repo (replace with your actual project URL)

What I changed in the project
----------------------------
- Added Rest Assured, Cucumber (TestNG) and TestNG dependencies in `pom.xml`.
- Implemented a minimal API framework in `src/test/java/com/reqres/api` with Endpoints, Services, Models, Steps, and a TestNG+Cucumber runner.
- Feature file `user_api.feature` contains CRUD scenarios based on the ReqRes docs.
- Removed the default sending of an API key and the skip-guard that required an explicit API key; ReqRes is public so tests run locally by default.

If you want stricter behavior (for example, skipping tests unless you explicitly opt-in with a key), I can reintroduce an opt-in flag.

Next steps I can do for you now
-------------------------------
- Add a Maven profile that allows passing proxy settings or other conveniences.
- Add more scenarios (list users, pagination, negative tests) following the official docs.
- Add request logging (toggleable) to help debug live runs."# reqres-java-rest-assured-api-tests"  
