package com.reqres.api.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.reqres.api.steps", "com.reqres.api.hooks"},
        plugin = {"pretty", "json:target/cucumber.json", "html:target/cucumber-html-report"},
        monochrome = true
)
public class TestRunnerTest extends AbstractTestNGCucumberTests {
    // This class will be picked up by Surefire (name ends with Test)
}