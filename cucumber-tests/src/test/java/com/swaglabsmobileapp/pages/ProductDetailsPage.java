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
 * Page Object Model for the Product Details Screen
 * Represents the screen shown when clicking on a product
 */
public class ProductDetailsPage {
    
    private AndroidDriver driver;
    private WebDriverWait wait;
    
    // Element locators using accessibility IDs
    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    private WebElement backToProductsButton;
    
    @AndroidFindBy(accessibility = "test-ADD TO CART")
    private WebElement addToCartButton;
    
    @AndroidFindBy(accessibility = "test-REMOVE")
    private WebElement removeButton;
    
    @AndroidFindBy(accessibility = "test-Description")
    private WebElement productDescription;
    
    @AndroidFindBy(accessibility = "test-Price")
    private WebElement productPrice;
    
    /**
     * Constructor - Initializes page elements using PageFactory
     * @param driver AndroidDriver instance
     */
    public ProductDetailsPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Check if product details screen is displayed
     * @return true if product details screen is visible
     */
    public boolean isProductDetailsScreenDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(backToProductsButton));
            return backToProductsButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click the Back to Products button
     */
    public void clickBackToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton));
        backToProductsButton.click();
    }
    
    /**
     * Check if Back to Products button is displayed
     * @return true if button is visible
     */
    public boolean isBackToProductsButtonDisplayed() {
        try {
            return backToProductsButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click Add to Cart button
     */
    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }
    
    /**
     * Click Remove button
     */
    public void clickRemove() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();
    }
    
    /**
     * Get product description text
     * @return Product description
     */
    public String getProductDescription() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productDescription));
            return productDescription.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Get product price text
     * @return Product price
     */
    public String getProductPrice() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productPrice));
            return productPrice.getText();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Check if Add to Cart button is displayed
     * @return true if button is visible
     */
    public boolean isAddToCartButtonDisplayed() {
        try {
            return addToCartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
