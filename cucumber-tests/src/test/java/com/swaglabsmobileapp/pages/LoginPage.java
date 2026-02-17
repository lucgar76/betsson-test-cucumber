package com.swaglabsmobileapp.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model for the Login Screen
 * Uses accessibility IDs from the React Native app for element identification
 */
public class LoginPage {
    
    private AndroidDriver driver;
    private WebDriverWait wait;
    
    // Element locators using accessibility IDs
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;
    
    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;
    
    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;
    
    @AndroidFindBy(accessibility = "test-Error message")
    private WebElement errorMessageContainer;
    
    @AndroidFindBy(xpath = "//*[@content-desc='test-Error message']//android.widget.TextView")
    private WebElement errorMessageText;
    
    @AndroidFindBy(accessibility = "test-Login")
    private WebElement loginScreen;
    
    /**
     * Constructor - Initializes page elements using PageFactory
     * @param driver AndroidDriver instance
     */
    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Wait for the login screen to be visible
     * @return true if login screen is displayed
     */
    public boolean isLoginScreenDisplayed() {
        try {
            System.out.println("Checking for login screen with accessibility ID: test-Login");
            wait.until(ExpectedConditions.visibilityOf(loginScreen));
            boolean isDisplayed = loginScreen.isDisplayed();
            System.out.println("Login screen displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("Login screen NOT found: " + e.getMessage());
            System.err.println("Current activity: " + driver.currentActivity());
            return false;
        }
    }
    
    /**
     * Enter username in the username field
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        if (username != null && !username.isEmpty()) {
            usernameField.sendKeys(username);
        }
    }
    
    /**
     * Enter password in the password field
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        if (password != null && !password.isEmpty()) {
            passwordField.sendKeys(password);
        }
    }
    
    /**
     * Click the login button
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
    
    /**
     * Perform complete login action with username and password
     * @param username Username to enter
     * @param password Password to enter
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessageContainer));
            return errorMessageContainer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get the error message text
     * @return Error message text
     */
    public String getErrorMessage() {
        try {
            // First wait for the error container to be visible
            wait.until(ExpectedConditions.visibilityOf(errorMessageContainer));
            System.out.println("DEBUG - Error container is visible");
            
            // Find all TextView elements within the error container
            List<WebElement> textViews = errorMessageContainer.findElements(By.className("android.widget.TextView"));
            System.out.println("DEBUG - Found " + textViews.size() + " TextView elements in error container");
            
            // Get text from all TextViews and combine them
            StringBuilder errorText = new StringBuilder();
            for (WebElement textView : textViews) {
                String text = textView.getText();
                if (text != null && !text.trim().isEmpty()) {
                    if (errorText.length() > 0) {
                        errorText.append(" ");
                    }
                    errorText.append(text);
                    System.out.println("DEBUG - TextView text: '" + text + "'");
                }
            }
            
            String finalText = errorText.toString().trim();
            System.out.println("DEBUG - Final error message: '" + finalText + "'");
            return finalText;
            
        } catch (Exception e) {
            System.err.println("DEBUG - Failed to get error message: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * Verify if error message contains expected text
     * @param expectedText Text to check in error message
     * @return true if error message contains the expected text
     */
    public boolean errorMessageContains(String expectedText) {
        String actualMessage = getErrorMessage();
        return actualMessage.contains(expectedText);
    }
    
    /**
     * Wait for error message to appear
     * @param timeout Timeout in seconds
     * @return true if error appears within timeout
     */
    public boolean waitForError(int timeout) {
        try {
            WebDriverWait errorWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            errorWait.until(ExpectedConditions.visibilityOf(errorMessageContainer));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if username field is displayed
     * @return true if username field is visible
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            return usernameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if password field is displayed
     * @return true if password field is visible
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            return passwordField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if login button is displayed
     * @return true if login button is visible
     */
    public boolean isLoginButtonDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
