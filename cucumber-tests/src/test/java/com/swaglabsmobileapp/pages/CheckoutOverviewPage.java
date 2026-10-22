package com.swaglabsmobileapp.pages;

import com.swaglabsmobileapp.utils.AndroidActions;
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
 * Page Object Model for the Checkout Overview Screen
 * Represents the order summary screen after entering checkout information
 */
public class CheckoutOverviewPage {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(accessibility = "test-FINISH")
    private WebElement finishButton;

    public CheckoutOverviewPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Check if the Checkout Overview screen is displayed.
     * Uses test-CHECKOUT: OVERVIEW (visible at top, no scroll needed).
     * FINISH button is below the fold and requires scrolling to find.
     */
    public boolean isCheckoutOverviewScreenDisplayed() {
        try {
            WebElement overviewIndicator = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    AppiumBy.accessibilityId("test-CHECKOUT: OVERVIEW")
                )
            );
            wait.until(ExpectedConditions.visibilityOf(overviewIndicator));
            return overviewIndicator.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll down to the bottom of the screen to reveal the FINISH button.
     */
    public void scrollToBottom() {
        try {
            AndroidActions.scrollToDescription(driver, "test-FINISH");
        } catch (Exception e) {
            System.err.println("Failed to scroll to the Finish button: " + e.getMessage());
        }
    }

    /**
     * Click the FINISH button. Selector: content-desc="test-FINISH" (parent ViewGroup).
     */
    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        finishButton.click();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
