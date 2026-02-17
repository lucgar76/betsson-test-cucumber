package com.swaglabsmobileapp.stepdefinitions;

import com.swaglabsmobileapp.pages.InventoryPage;
import com.swaglabsmobileapp.pages.ProductDetailsPage;
import com.swaglabsmobileapp.utils.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step Definitions for Product Navigation Feature
 * Maps Gherkin steps to Java code for product viewing and navigation
 */
public class ProductNavigationSteps {
    
    private InventoryPage inventoryPage;
    private ProductDetailsPage productDetailsPage;
    
    /**
     * Initialize page objects
     */
    public ProductNavigationSteps() {
        this.inventoryPage = new InventoryPage(DriverManager.getDriver());
        this.productDetailsPage = new ProductDetailsPage(DriverManager.getDriver());
    }
    
    @When("I click on product {int}")
    public void iClickOnProduct(int productIndex) {
        inventoryPage.clickProductByIndex(productIndex);
        System.out.println("Clicked on product " + productIndex);
    }
    
    @Then("I should see the product details screen")
    public void iShouldSeeTheProductDetailsScreen() {
        Assert.assertTrue("Product details screen should be displayed", 
                         productDetailsPage.isProductDetailsScreenDisplayed());
        Assert.assertTrue("Back to Products button should be visible", 
                         productDetailsPage.isBackToProductsButtonDisplayed());
    }
    
    @When("I tap the Back to Products button")
    public void iTapTheBackToProductsButton() {
        productDetailsPage.clickBackToProducts();
        System.out.println("Clicked Back to Products button");
    }
    
    @When("I tap on the view toggle button")
    public void iTapOnTheViewToggleButton() {
        inventoryPage.clickViewToggle();
        System.out.println("Tapped on view toggle button");
    }
    
    @Then("I should see the products in list view")
    public void iShouldSeeTheProductsInListView() {
        Assert.assertTrue("Products should be displayed in list view", 
                         inventoryPage.isListViewDisplayed());
        System.out.println("Products are displayed in list view");
    }
    
    @When("I scroll down to the bottom of the page")
    public void iScrollDownToTheBottomOfThePage() {
        inventoryPage.scrollToBottom();
        System.out.println("Scrolled down to the bottom of the page");
    }
}
