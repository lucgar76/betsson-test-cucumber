package com.swaglabsmobileapp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads configuration properties from config.properties file
 */
public class ConfigReader {
    
    private static Properties properties;
    private static final String CONFIG_FILE_NAME = "config.properties";
    
    /**
     * Load properties from config file (from classpath)
     */
    static {
        properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE_NAME)) {
            if (input == null) {
                System.err.println("Unable to find " + CONFIG_FILE_NAME + " in classpath");
                throw new IOException(CONFIG_FILE_NAME + " not found in classpath");
            }
            properties.load(input);
            System.out.println("Configuration loaded successfully from classpath: " + CONFIG_FILE_NAME);
            
            // Debug: Print fullReset value to confirm it's loaded correctly
            System.out.println("DEBUG - fullReset value: " + properties.getProperty("full.reset"));
            System.out.println("DEBUG - noReset value: " + properties.getProperty("no.reset"));
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Property not found: " + key);
        }
        return value;
    }
    
    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get Appium server URL
     * @return Appium server URL
     */
    public static String getAppiumUrl() {
        return getProperty("appium.server.url", "http://127.0.0.1:4723");
    }
    
    /**
     * Get platform name (Android/iOS)
     * @return Platform name
     */
    public static String getPlatformName() {
        return getProperty("platform.name", "Android");
    }
    
    /**
     * Get device name
     * @return Device name
     */
    public static String getDeviceName() {
        return getProperty("device.name", "Android Emulator");
    }
    
    /**
     * Get automation name
     * @return Automation name
     */
    public static String getAutomationName() {
        return getProperty("automation.name", "UiAutomator2");
    }
    
    /**
     * Get app package name
     * @return App package
     */
    public static String getAppPackage() {
        return getProperty("app.package", "com.swaglabsmobileapp");
    }
    
    /**
     * Get app activity name
     * @return App activity
     */
    public static String getAppActivity() {
        return getProperty("app.activity", ".MainActivity");
    }
    
    /**
     * Get app path (APK file location)
     * @return App path
     */
    public static String getAppPath() {
        return getProperty("app.path");
    }
    
    /**
     * Get implicit wait timeout
     * @return Implicit wait in seconds
     */
    public static int getImplicitWait() {
        String wait = getProperty("implicit.wait", "10");
        return Integer.parseInt(wait);
    }
    
    /**
     * Get explicit wait timeout
     * @return Explicit wait in seconds
     */
    public static int getExplicitWait() {
        String wait = getProperty("explicit.wait", "15");
        return Integer.parseInt(wait);
    }
    
    /**
     * Get noReset capability value
     * @return true if noReset should be enabled
     */
    public static boolean getNoReset() {
        String noReset = getProperty("no.reset", "false");
        return Boolean.parseBoolean(noReset);
    }
    
    /**
     * Get fullReset capability value
     * @return true if fullReset should be enabled
     */
    public static boolean getFullReset() {
        String fullReset = getProperty("full.reset", "false");
        return Boolean.parseBoolean(fullReset);
    }
    
    /**
     * Get platform version
     * @return Platform version
     */
    public static String getPlatformVersion() {
        return getProperty("platform.version", "");
    }
    
    /**
     * Print all loaded properties (for debugging)
     */
    public static void printAllProperties() {
        System.out.println("========== Configuration Properties ==========");
        properties.forEach((key, value) -> 
            System.out.println(key + " = " + value)
        );
        System.out.println("=============================================");
    }
}
