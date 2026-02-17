package com.swaglabsmobileapp.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

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
            Dimension size = driver.manage().window().getSize();
            int width = size.getWidth();
            int height = size.getHeight();
            int startX = width / 2;
            int startY = (int) (height * 0.8);
            int endX = width / 2;
            int endY = (int) (height * 0.2);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence scroll = new Sequence(finger, 0);
            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(scroll));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            // Ignore scroll failures
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
