package com.swaglabsmobileapp.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for the Side Menu
 * Represents the menu that appears after clicking the burger menu
 */
public class MenuPage {
    
    private AndroidDriver driver;
    private WebDriverWait wait;
    
    // Element locators using accessibility IDs
    @AndroidFindBy(accessibility = "test-LOGOUT")
    private WebElement logoutButton;
    
    @AndroidFindBy(accessibility = "test-ALL ITEMS")
    private WebElement allItemsButton;
    
    @AndroidFindBy(accessibility = "test-ABOUT")
    private WebElement aboutButton;
    
    @AndroidFindBy(accessibility = "test-RESET APP STATE")
    private WebElement resetAppStateButton;
    
    /**
     * Constructor - Initializes page elements using PageFactory
     * @param driver AndroidDriver instance
     */
    public MenuPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Click the logout button in the side menu
     */
    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutButton.click();
    }
    
    /**
     * Check if logout button is displayed
     * @return true if logout button is visible
     */
    public boolean isLogoutButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutButton));
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click All Items menu option
     */
    public void clickAllItems() {
        wait.until(ExpectedConditions.elementToBeClickable(allItemsButton));
        allItemsButton.click();
    }
    
    /**
     * Click About menu option
     */
    public void clickAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutButton));
        aboutButton.click();
    }
    
    /**
     * Click Reset App State menu option
     */
    public void clickResetAppState() {
        wait.until(ExpectedConditions.elementToBeClickable(resetAppStateButton));
        resetAppStateButton.click();
    }
}
