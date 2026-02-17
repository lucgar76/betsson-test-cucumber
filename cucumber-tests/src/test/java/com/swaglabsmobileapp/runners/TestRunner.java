package com.swaglabsmobileapp.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber Test Runner
 * Configures and executes Cucumber tests with JUnit
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    // Feature files location
    features = "src/test/resources/features",
    
    // Step definitions package
    glue = {"com.swaglabsmobileapp.stepdefinitions"},
    
    // Report plugins
    plugin = {
        "pretty",                                    // Console output with colors
        "html:target/cucumber-reports/cucumber.html", // HTML report
        "json:target/cucumber-reports/cucumber.json", // JSON report
        "junit:target/cucumber-reports/cucumber.xml"  // JUnit XML report
    },
    
    // Run scenarios with specific tags
    // tags = "@smoke", // Uncomment to run only smoke tests
    
    // Ensure scenarios are executed in order
    monochrome = true,
    
    // Print step definitions to console
    publish = true,
    
    // Dry run - check if all steps have definitions (for validation)
    dryRun = false
)
public class TestRunner {
    // This class will be empty - JUnit uses the annotations to run tests
}
