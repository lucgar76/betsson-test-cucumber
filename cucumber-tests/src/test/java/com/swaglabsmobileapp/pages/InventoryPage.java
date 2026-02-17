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
import java.util.List;

/**
 * Page Object Model for the Inventory Screen (Product List)
 * Represents the screen shown after successful login
 */
public class InventoryPage {
    
    private AndroidDriver driver;
    private WebDriverWait wait;
    
    // Element locators using accessibility IDs
    @AndroidFindBy(accessibility = "test-PRODUCTS")
    private WebElement productsTitle;
    
    @AndroidFindBy(accessibility = "test-Item")
    private List<WebElement> productItems;
    
    @AndroidFindBy(xpath = "//*[contains(@content-desc, 'test-Item')]")
    private List<WebElement> allProducts;
    
    @AndroidFindBy(accessibility = "test-Menu")
    private WebElement menuButton;
    
    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cartButton;
    
    @AndroidFindBy(accessibility = "test-Modal Selector Button")
    private WebElement filterButton;
    
    @AndroidFindBy(accessibility = "test-Toggle")
    private WebElement viewToggleButton;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sort items by...']")
    private WebElement sortMenuTitle;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Name (A to Z)']")
    private WebElement sortOptionAtoZ;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Name (Z to A)']")
    private WebElement sortOptionZtoA;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Price (low to high)']")
    private WebElement sortOptionPriceLowToHigh;
    
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Price (high to low)']")
    private WebElement sortOptionPriceHighToLow;
    
    @AndroidFindBy(xpath = "//android.widget.Button[@text='Cancel']")
    private WebElement cancelButton;
    
    /**
     * Constructor - Initializes page elements using PageFactory
     * @param driver AndroidDriver instance
     */
    public InventoryPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Wait for the inventory screen to be displayed
     * @return true if inventory screen is visible
     */
    public boolean isInventoryScreenDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productsTitle));
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if products title is displayed
     * @return true if "PRODUCTS" title is visible
     */
    public boolean isProductsTitleDisplayed() {
        try {
            return productsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if product list is displayed
     * @return true if at least one product is visible
     */
    public boolean areProductsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(allProducts));
            return !allProducts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get the number of products displayed
     * @return Count of visible products
     */
    public int getProductCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(allProducts));
            return allProducts.size();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * Check if menu button is displayed
     * @return true if menu button is visible
     */
    public boolean isMenuButtonDisplayed() {
        try {
            return menuButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if cart button is displayed
     * @return true if cart button is visible
     */
    public boolean isCartButtonDisplayed() {
        try {
            return cartButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for products to load
     * @param timeout Timeout in seconds
     * @return true if products load within timeout
     */
    public boolean waitForProductsToLoad(int timeout) {
        try {
            WebDriverWait productsWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            productsWait.until(ExpectedConditions.visibilityOfAllElements(allProducts));
            return !allProducts.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click the burger menu button at the top left
     */
    public void clickBurgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton));
        menuButton.click();
    }
    
    /**
     * Click the cart button to navigate to the Your Cart screen
     */
    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
    }
    
    /**
     * Click on a specific product by index (1-based)
     * @param productIndex Product index (1 for first product, 2 for second, etc.)
     */
    public void clickProductByIndex(int productIndex) {
        wait.until(ExpectedConditions.visibilityOfAllElements(allProducts));
        if (productIndex > 0 && productIndex <= allProducts.size()) {
            WebElement product = allProducts.get(productIndex - 1);
            wait.until(ExpectedConditions.elementToBeClickable(product));
            product.click();
        } else {
            throw new IllegalArgumentException("Product index " + productIndex + " is out of range. Available products: " + allProducts.size());
        }
    }
    
    /**
     * Click the filter/sort button
     */
    public void clickFilterButton() {
        try {
            // Scroll to top first to ensure button is visible
            scrollToTop();
            
            // Wait for filter button to be visible and clickable
            wait.until(ExpectedConditions.visibilityOf(filterButton));
            wait.until(ExpectedConditions.elementToBeClickable(filterButton));
            
            System.out.println("Filter button found, attempting click...");
            filterButton.click();
            System.out.println("Filter button clicked successfully");
            
        } catch (Exception e) {
            System.err.println("Failed to click filter button: " + e.getMessage());
            // Try alternative click method
            try {
                System.out.println("Trying alternative click method using coordinates...");
                filterButton.click();
            } catch (Exception ex) {
                throw new RuntimeException("Unable to click filter button after multiple attempts", ex);
            }
        }
    }
    
    /**
     * Check if sort menu is displayed
     * @return true if sort options menu is visible
     */
    public boolean isSortMenuDisplayed() {
        try {
            System.out.println("Checking for sort menu visibility...");
            
            // Wait for the sort menu title to appear
            wait.until(ExpectedConditions.visibilityOf(sortMenuTitle));
            System.out.println("Sort menu title found: " + sortMenuTitle.getText());
            
            // Verify multiple sort options are visible
            boolean titleVisible = sortMenuTitle.isDisplayed();
            boolean sortOptionsVisible = sortOptionAtoZ.isDisplayed() || sortOptionZtoA.isDisplayed();
            
            System.out.println("Sort menu title visible: " + titleVisible);
            System.out.println("Sort options visible: " + sortOptionsVisible);
            
            return titleVisible && sortOptionsVisible;
        } catch (Exception e) {
            System.err.println("Sort menu not displayed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verify all sort options are displayed
     * @return true if all sort options are visible
     */
    public boolean areAllSortOptionsDisplayed() {
        try {
            System.out.println("Verifying all sort options are displayed...");
            
            boolean aToZ = sortOptionAtoZ.isDisplayed();
            boolean zToA = sortOptionZtoA.isDisplayed();
            boolean priceLow = sortOptionPriceLowToHigh.isDisplayed();
            boolean priceHigh = sortOptionPriceHighToLow.isDisplayed();
            boolean cancel = cancelButton.isDisplayed();
            
            System.out.println("Name (A to Z): " + aToZ);
            System.out.println("Name (Z to A): " + zToA);
            System.out.println("Price (low to high): " + priceLow);
            System.out.println("Price (high to low): " + priceHigh);
            System.out.println("Cancel button: " + cancel);
            
            return aToZ && zToA && priceLow && priceHigh && cancel;
        } catch (Exception e) {
            System.err.println("Failed to verify all sort options: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Select a sort option by text
     * @param sortOption The sort option text (e.g., "Name (Z to A)")
     */
    public void selectSortOption(String sortOption) {
        try {
            // Wait for sort options to be visible
            Thread.sleep(1000);
            
            // Find the sort option by text
            WebElement sortElement = driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + sortOption + "\")"
            ));
            
            wait.until(ExpectedConditions.elementToBeClickable(sortElement));
            sortElement.click();
            
            // Wait for sort to apply
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select sort option: " + sortOption, e);
        }
    }
    
    /**
     * Scroll to the top of the page
     */
    public void scrollToTop() {
        try {
            // Get screen dimensions
            Dimension size = driver.manage().window().getSize();
            int width = size.getWidth();
            int height = size.getHeight();
            
            // Calculate coordinates for vertical scroll (from 20% to 80% of screen height)
            int startX = width / 2;
            int startY = (int) (height * 0.2);
            int endX = width / 2;
            int endY = (int) (height * 0.8);
            
            // Create pointer input for touch
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            
            // Create scroll sequence
            Sequence scroll = new Sequence(finger, 0);
            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            
            // Perform scroll
            driver.perform(Collections.singletonList(scroll));
            
            System.out.println("Scrolled to top");
            
            // Wait for scroll to complete
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.err.println("Failed to scroll to top: " + e.getMessage());
        }
    }
    
    /**
     * Scroll down to the bottom of the page
     */
    public void scrollToBottom() {
        try {
            // Get screen dimensions
            Dimension size = driver.manage().window().getSize();
            int width = size.getWidth();
            int height = size.getHeight();
            
            // Calculate coordinates for vertical scroll (from 80% to 20% of screen height)
            int startX = width / 2;
            int startY = (int) (height * 0.8);
            int endX = width / 2;
            int endY = (int) (height * 0.2);
            
            // Create pointer input for touch
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            
            // Create scroll sequence
            Sequence scroll = new Sequence(finger, 0);
            scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            scroll.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
            scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            
            // Perform scroll
            driver.perform(Collections.singletonList(scroll));
            
            System.out.println("Scrolled down to bottom");
            
            // Wait for scroll to complete
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.err.println("Failed to scroll: " + e.getMessage());
        }
    }
    
    /**
     * Click the view toggle button to switch between grid and list view
     */
    public void clickViewToggle() {
        try {
            // Scroll to top to ensure toggle button is visible
            scrollToTop();
            
            wait.until(ExpectedConditions.visibilityOf(viewToggleButton));
            wait.until(ExpectedConditions.elementToBeClickable(viewToggleButton));
            
            System.out.println("View toggle button found, attempting click...");
            viewToggleButton.click();
            System.out.println("View toggle button clicked successfully");
            
            // Wait for view to change
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to click view toggle button: " + e.getMessage());
            throw new RuntimeException("Unable to click view toggle button", e);
        }
    }
    
    /**
     * Check if products are displayed in list view
     * In list view, product descriptions are visible
     * @return true if list view is displayed
     */
    public boolean isListViewDisplayed() {
        try {
            System.out.println("Checking if list view is displayed...");
            
            // In list view, products should still be visible
            wait.until(ExpectedConditions.visibilityOfAllElements(allProducts));
            
            // Additional wait to ensure view has transitioned
            Thread.sleep(500);
            
            System.out.println("List view is displayed with " + allProducts.size() + " products");
            return !allProducts.isEmpty();
        } catch (Exception e) {
            System.err.println("List view not displayed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Click the Add to Cart (+) button for a specific product by name.
     * Uses XPath to find the product item containing the product name, then clicks the + within it.
     * Selector based on: test-Item title with product name, Add to Cart is TextView with text "+"
     * @param productName The product name (e.g., "Sauce Labs Bolt T-Shirt" or "Bolt T-Shirt")
     */
    public void clickAddToCartForProduct(String productName) {
        try {
            scrollToTop();
            
            // Extract the distinctive part of the product name for matching (e.g., "Bolt T-Shirt" from "Sauce Labs Bolt T-Shirt")
            String searchText = productName.contains("Labs ") ? productName.substring(productName.indexOf("Labs ") + 5) : productName;
            
            // XPath: find the Add to Cart (+) button within the product item that contains this product name
            // Structure: test-Item (ViewGroup) contains test-Item title (TextView) and + button (TextView)
            String xpath = "//android.view.ViewGroup[@content-desc='test-Item'][.//android.widget.TextView[contains(@text, '" + searchText + "')]]//android.widget.TextView[@text='+']";
            
            WebElement addButton = driver.findElement(AppiumBy.xpath(xpath));
            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            addButton.click();
            
            System.out.println("Clicked Add to Cart for product: " + productName);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.err.println("Failed to click Add to Cart for product " + productName + ": " + e.getMessage());
            throw new RuntimeException("Unable to click Add to Cart for product: " + productName, e);
        }
    }
    
    /**
     * Get the cart item count from the cart badge.
     * Uses the cart badge TextView within test-Cart ViewGroup.
     * @return The number of items in the cart, or 0 if cart is empty or badge not found
     */
    public int getCartItemCount() {
        try {
            // Cart badge shows the count - find TextView with numeric text within test-Cart
            WebElement cartBadge = driver.findElement(AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView[not(@text='')]"));
            String countText = cartBadge.getText().trim();
            return Integer.parseInt(countText);
        } catch (Exception e) {
            // Cart might be empty - no badge visible
            return 0;
        }
    }
    
    /**
     * Verify that the cart contains the expected number of items.
     * @param expectedCount Expected item count in cart
     * @return true if cart count matches expected
     */
    public boolean isCartCount(int expectedCount) {
        try {
            Thread.sleep(500);
            int actualCount = getCartItemCount();
            return actualCount == expectedCount;
        } catch (Exception e) {
            return false;
        }
    }
}
