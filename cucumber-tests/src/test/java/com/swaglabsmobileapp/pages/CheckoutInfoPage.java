package com.swaglabsmobileapp.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for the Checkout Information Screen
 * Represents the screen where user enters shipping/payment details
 */
public class CheckoutInfoPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(accessibility = "test-First Name")
    private WebElement firstNameInput;

    @AndroidFindBy(accessibility = "test-Last Name")
    private WebElement lastNameInput;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement zipCodeInput;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    public CheckoutInfoPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Check if the Checkout Information screen is displayed.
     * Waits for the First Name field to be present and visible (content-desc="test-First Name").
     */
    public boolean isCheckoutInfoScreenDisplayed() {
        try {
            WebElement checkoutIndicator = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("test-First Name")
                )
            );
            wait.until(ExpectedConditions.visibilityOf(checkoutIndicator));
            return checkoutIndicator.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enter First Name. Selector: content-desc="test-First Name"
     */
    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    /**
     * Enter Last Name. Selector: content-desc="test-Last Name"
     */
    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameInput));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    /**
     * Enter Zip/Postal Code. Selector: content-desc="test-Zip/Postal Code"
     */
    public void enterZipCode(String zipCode) {
        wait.until(ExpectedConditions.visibilityOf(zipCodeInput));
        zipCodeInput.clear();
        zipCodeInput.sendKeys(zipCode);
    }

    /**
     * Tap the CONTINUE button to proceed to checkout overview.
     * Selector: content-desc="test-CONTINUE"
     */
    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }
}
