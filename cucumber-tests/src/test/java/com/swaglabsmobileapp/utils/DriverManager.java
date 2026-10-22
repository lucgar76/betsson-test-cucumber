package com.swaglabsmobileapp.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Appium Driver Manager
 * Manages AndroidDriver lifecycle using singleton pattern
 */
public class DriverManager {
    
    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;
    
    /**
     * Initialize Appium driver with capabilities
     */
    public static void initializeDriver() {
        if (driver == null) {
            try {
                // Configure desired capabilities
                UiAutomator2Options options = new UiAutomator2Options();
                
                // Platform capabilities
                options.setPlatformName(ConfigReader.getPlatformName());
                options.setDeviceName(ConfigReader.getDeviceName());
                options.setAutomationName(ConfigReader.getAutomationName());
                
                // App capabilities. A missing APK falls back to the installed package.
                File appFile = resolveAppFile(ConfigReader.getAppPath());
                if (appFile != null) {
                    options.setApp(appFile.getAbsolutePath());
                    System.out.println("Using app file: " + appFile.getAbsolutePath());
                } else {
                    options.setAppPackage(ConfigReader.getAppPackage());
                    options.setAppActivity(ConfigReader.getAppActivity());
                    System.out.println("Using app package: " + ConfigReader.getAppPackage());
                    System.out.println("Using app activity: " + ConfigReader.getAppActivity());
                }

                // Platform version (optional)
                String platformVersion = ConfigReader.getPlatformVersion();
                if (platformVersion != null && !platformVersion.isEmpty()) {
                    options.setPlatformVersion(platformVersion);
                }

                // Reset capabilities
                options.setNoReset(ConfigReader.getNoReset());
                options.setFullReset(ConfigReader.getFullReset());

                // SplashActivity hands off to MainActivity; wait for that before the steps run.
                options.setAppWaitActivity("com.swaglabsmobileapp.MainActivity");
                options.setAppWaitDuration(Duration.ofSeconds(120));

                options.setNewCommandTimeout(Duration.ofSeconds(300));
                options.setAutoGrantPermissions(true);
                options.setDisableWindowAnimation(true);
                options.setIgnoreHiddenApiPolicyError(true);
                // Cold emulators can take longer than the driver defaults to grant
                // permissions and to bring the UiAutomator2 instrumentation online.
                options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(120));
                options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(120));
                options.setAdbExecTimeout(Duration.ofSeconds(120));
                
                // Create driver
                String appiumUrl = ConfigReader.getAppiumUrl();
                System.out.println("Connecting to Appium server: " + appiumUrl);
                
                driver = new AndroidDriver(new URL(appiumUrl), options);
                
                // Explicit waits are used in the page objects. An implicit wait makes those
                // polls wait on every lookup and turns short failures into long ones.
                driver.manage().timeouts().implicitlyWait(Duration.ZERO);
                
                System.out.println("Appium driver initialized successfully");
                System.out.println("Session ID: " + driver.getSessionId());
                
            } catch (MalformedURLException e) {
                System.err.println("Invalid Appium server URL: " + e.getMessage());
                throw new RuntimeException("Failed to initialize driver", e);
            } catch (Exception e) {
                System.err.println("Failed to initialize Appium driver: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize driver", e);
            }
        }
    }
    
    /**
     * Get the current driver instance
     * @return AndroidDriver instance
     */
    public static AndroidDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }
    
    /**
     * Quit the driver and clean up
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                System.out.println("Closing Appium driver...");
                driver.quit();
                System.out.println("Appium driver closed successfully");
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
        
        if (service != null && service.isRunning()) {
            try {
                System.out.println("Stopping Appium service...");
                service.stop();
                System.out.println("Appium service stopped");
            } catch (Exception e) {
                System.err.println("Error while stopping Appium service: " + e.getMessage());
            } finally {
                service = null;
            }
        }
    }
    
    /**
     * Start local Appium server programmatically (optional)
     * This is an alternative to running Appium server manually
     */
    public static void startAppiumService() {
        if (service == null || !service.isRunning()) {
            try {
                System.out.println("Starting local Appium service...");
                service = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .build();
                service.start();
                System.out.println("Appium service started successfully");
            } catch (Exception e) {
                System.err.println("Failed to start Appium service: " + e.getMessage());
                throw new RuntimeException("Failed to start Appium service", e);
            }
        }
    }
    
    /**
     * Check if driver is initialized
     * @return true if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driver != null;
    }

    /**
     * Resolve a configured APK path from the working directory.
     * Maven is normally started from cucumber-tests, where app.path is ../android/...
     * @param appPath configured path, possibly relative
     * @return existing APK file, or null when the app should be launched by package
     */
    private static File resolveAppFile(String appPath) {
        if (appPath == null || appPath.isEmpty()) {
            return null;
        }

        File direct = new File(appPath);
        if (direct.isAbsolute() && direct.exists()) {
            return direct;
        }

        File fromWorkingDirectory = new File(System.getProperty("user.dir"), appPath);
        if (fromWorkingDirectory.exists()) {
            return fromWorkingDirectory;
        }

        File fromModule = new File(System.getProperty("user.dir"), "cucumber-tests/" + appPath);
        if (fromModule.exists()) {
            return fromModule;
        }

        System.err.println("App file not found: " + appPath + ". Falling back to the installed package.");
        return null;
    }
}
