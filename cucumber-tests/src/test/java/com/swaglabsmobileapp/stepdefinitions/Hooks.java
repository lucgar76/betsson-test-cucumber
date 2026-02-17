package com.swaglabsmobileapp.stepdefinitions;

import com.swaglabsmobileapp.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cucumber Hooks for setup and teardown
 * Manages Appium driver lifecycle and screenshot capture
 */
public class Hooks {
    
    private static final String SCREENSHOT_DIR = "target/screenshots/";
    
    /**
     * Setup hook - Runs before each scenario
     * Initializes Appium driver and launches the app
     * @param scenario Current scenario
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("========================================");
        
        try {
            // Initialize driver through DriverManager
            DriverManager.initializeDriver();
            System.out.println("Appium driver initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize Appium driver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    
    /**
     * Teardown hook - Runs after each scenario
     * Captures screenshot on failure and quits driver
     * Note: fullReset=true in config.properties ensures app is uninstalled/reinstalled before next test
     * @param scenario Current scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            // Capture screenshot if scenario failed
            if (scenario.isFailed()) {
                System.out.println("Scenario FAILED: " + scenario.getName());
                captureScreenshot(scenario);
            } else {
                System.out.println("Scenario PASSED: " + scenario.getName());
            }
            
            // Quit driver - fullReset=true will uninstall/reinstall app before next test
            DriverManager.quitDriver();
            System.out.println("Appium driver closed successfully");
            
        } catch (Exception e) {
            System.err.println("Error during teardown: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("========================================");
        System.out.println("Finished Scenario: " + scenario.getName());
        System.out.println("========================================\n");
    }
    
    /**
     * After step hook - Runs after each step
     * Can be used to capture screenshots after each step if needed
     * @param scenario Current scenario
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Optional: Capture screenshot after each step
        // Uncomment the line below to enable step-by-step screenshots
        // captureScreenshot(scenario, "step");
    }
    
    /**
     * Capture screenshot and attach to Cucumber report
     * @param scenario Current scenario
     */
    private void captureScreenshot(Scenario scenario) {
        try {
            AndroidDriver driver = DriverManager.getDriver();
            if (driver != null) {
                // Take screenshot as byte array
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                
                // Attach to Cucumber report
                scenario.attach(screenshot, "image/png", scenario.getName());
                
                // Save to file system
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                String fileName = scenarioName + "_" + timestamp + ".png";
                
                saveScreenshotToFile(screenshot, fileName);
                
                System.out.println("Screenshot captured: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Capture screenshot with custom suffix
     * @param scenario Current scenario
     * @param suffix Screenshot suffix (e.g., "step", "error")
     */
    private void captureScreenshot(Scenario scenario, String suffix) {
        try {
            AndroidDriver driver = DriverManager.getDriver();
            if (driver != null) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String scenarioName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_");
                String fileName = scenarioName + "_" + suffix + "_" + timestamp + ".png";
                
                saveScreenshotToFile(screenshot, fileName);
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Save screenshot to file system
     * @param screenshot Screenshot as byte array
     * @param fileName File name
     */
    private void saveScreenshotToFile(byte[] screenshot, String fileName) {
        try {
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            File screenshotFile = new File(SCREENSHOT_DIR + fileName);
            FileUtils.writeByteArrayToFile(screenshotFile, screenshot);
            
            System.out.println("Screenshot saved to: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save screenshot to file: " + e.getMessage());
        }
    }
}
