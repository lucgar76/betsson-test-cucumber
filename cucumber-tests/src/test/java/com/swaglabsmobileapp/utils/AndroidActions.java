package com.swaglabsmobileapp.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Shared Android interactions used by the page objects.
 */
public final class AndroidActions {

    private AndroidActions() {
    }

    public static void hideKeyboard(AndroidDriver driver) {
        try {
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }
        } catch (Exception ignored) {
            // The keyboard is already hidden, or this device has no soft keyboard.
        }
    }

    /**
     * Type into an input. React Native often puts the accessibility id on a wrapper,
     * so the real EditText may be a child of the element the page object found.
     */
    public static void typeInto(AndroidDriver driver, WebElement element, String text) {
        WebElement input = element;
        try {
            String className = element.getAttribute("className");
            if (className == null || !className.contains("EditText")) {
                List<WebElement> edits = element.findElements(By.className("android.widget.EditText"));
                if (!edits.isEmpty()) {
                    input = edits.get(0);
                }
            }
        } catch (Exception ignored) {
            // Use the original element.
        }

        input.click();
        try {
            input.clear();
        } catch (Exception ignored) {
            // Some React Native inputs reject clear(); sendKeys still replaces the value.
        }

        if (text == null || text.isEmpty()) {
            return;
        }

        // sendKeys goes through the keyboard path, so React Native onChangeText runs.
        // replaceElementValue sets the native text without updating component state.
        input.sendKeys(text);
    }

    public static void scrollToDescription(AndroidDriver driver, String contentDesc) {
        String selector = "new UiScrollable(new UiSelector().scrollable(true))"
                + ".setMaxSearchSwipes(8)"
                + ".scrollIntoView(new UiSelector().description(\"" + contentDesc + "\"))";
        driver.findElement(AppiumBy.androidUIAutomator(selector));
    }

    public static void scrollToText(AndroidDriver driver, String text) {
        String selector = "new UiScrollable(new UiSelector().scrollable(true))"
                + ".setMaxSearchSwipes(8)"
                + ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))";
        driver.findElement(AppiumBy.androidUIAutomator(selector));
    }
}
