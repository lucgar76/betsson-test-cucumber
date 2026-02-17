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
 * Page Object Model for the Your Cart Screen
 * Represents the cart screen shown when tapping the cart icon
 */
public class CartPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YOUR CART']")
    private WebElement yourCartTitle;

    @AndroidFindBy(accessibility = "test-CHECKOUT")
    private WebElement checkoutButton;

    @AndroidFindBy(accessibility = "test-CONTINUE SHOPPING")
    private WebElement continueShoppingButton;

    public CartPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Check if the Your Cart screen is displayed
     */
    public boolean isYourCartScreenDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(yourCartTitle));
            return yourCartTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click the CHECKOUT button
     * Selector: content-desc="test-CHECKOUT" or text="CHECKOUT"
     */
    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
        // Allow screen transition to Checkout Information
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
