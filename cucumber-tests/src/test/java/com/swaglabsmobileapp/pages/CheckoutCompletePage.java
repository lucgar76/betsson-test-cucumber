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
 * Page Object Model for the Checkout Complete Screen
 * Represents the success screen after placing an order
 */
public class CheckoutCompletePage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(accessibility = "test-BACK HOME")
    private WebElement backHomeButton;

    public CheckoutCompletePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Check if the Checkout Complete screen is displayed.
     * Verifies by presence of "CHECKOUT: COMPLETE!" or BACK HOME button.
     */
    public boolean isCheckoutCompleteScreenDisplayed() {
        try {
            WebElement completeIndicator = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("test-BACK HOME")
                )
            );
            wait.until(ExpectedConditions.visibilityOf(completeIndicator));
            return completeIndicator.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click the BACK HOME button. Selector: content-desc="test-BACK HOME".
     */
    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(backHomeButton));
        backHomeButton.click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
