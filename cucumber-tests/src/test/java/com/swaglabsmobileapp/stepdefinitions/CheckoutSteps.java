package com.swaglabsmobileapp.stepdefinitions;

import com.swaglabsmobileapp.pages.CartPage;
import com.swaglabsmobileapp.pages.CheckoutCompletePage;
import com.swaglabsmobileapp.pages.CheckoutInfoPage;
import com.swaglabsmobileapp.pages.CheckoutOverviewPage;
import com.swaglabsmobileapp.pages.InventoryPage;
import com.swaglabsmobileapp.utils.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step Definitions for Checkout Feature
 * Maps Gherkin steps to Java code for adding products to cart and checkout flow
 */
public class CheckoutSteps {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    public CheckoutSteps() {
        this.inventoryPage = new InventoryPage(DriverManager.getDriver());
        this.cartPage = new CartPage(DriverManager.getDriver());
        this.checkoutInfoPage = new CheckoutInfoPage(DriverManager.getDriver());
        this.checkoutOverviewPage = new CheckoutOverviewPage(DriverManager.getDriver());
        this.checkoutCompletePage = new CheckoutCompletePage(DriverManager.getDriver());
    }

    @And("I tap the add to cart button for the product {string}")
    public void iTapTheAddToCartButtonForTheProduct(String productName) {
        inventoryPage.clickAddToCartForProduct(productName);
        System.out.println("Tapped Add to Cart for product: " + productName);
    }

    @Then("the product should be added to the cart")
    public void theProductShouldBeAddedToTheCart() {
        Assert.assertTrue("Product should be in cart (cart badge should show 1)",
                inventoryPage.isCartCount(1));
        System.out.println("Product successfully added to cart - cart count is 1");
    }

    @When("I tap on the cart")
    public void iTapOnTheCart() {
        inventoryPage.clickCart();
        System.out.println("Tapped on cart");
    }

    @Then("I should be on the Your Cart screen")
    public void iShouldBeOnTheYourCartScreen() {
        Assert.assertTrue("Your Cart screen should be displayed",
                cartPage.isYourCartScreenDisplayed());
        System.out.println("Navigated to Your Cart screen");
    }

    @When("I tap the Checkout button")
    public void iTapTheCheckoutButton() {
        cartPage.clickCheckout();
        System.out.println("Tapped Checkout button");
    }

    @Then("I should be on the Checkout Information screen")
    public void iShouldBeOnTheCheckoutInformationScreen() {
        Assert.assertTrue("Checkout Information screen should be displayed",
                checkoutInfoPage.isCheckoutInfoScreenDisplayed());
        System.out.println("Navigated to Checkout Information screen");
    }

    @When("I enter First Name {string}")
    public void iEnterFirstName(String firstName) {
        checkoutInfoPage.enterFirstName(firstName);
        System.out.println("Entered First Name: " + firstName);
    }

    @When("I enter Last Name {string}")
    public void iEnterLastName(String lastName) {
        checkoutInfoPage.enterLastName(lastName);
        System.out.println("Entered Last Name: " + lastName);
    }

    @When("I enter Zip\\/Postal Code {string}")
    public void iEnterZipPostalCode(String zipCode) {
        checkoutInfoPage.enterZipCode(zipCode);
        System.out.println("Entered Zip/Postal Code: " + zipCode);
    }

    @When("I tap the Continue button")
    public void iTapTheContinueButton() {
        checkoutInfoPage.clickContinue();
        System.out.println("Tapped Continue button");
    }

    @Then("I should be on the Checkout Overview screen")
    public void iShouldBeOnTheCheckoutOverviewScreen() {
        Assert.assertTrue("Checkout Overview screen should be displayed",
                checkoutOverviewPage.isCheckoutOverviewScreenDisplayed());
        System.out.println("Navigated to Checkout Overview screen");
    }

    @When("I scroll down to the bottom of the screen")
    public void iScrollDownToTheBottomOfTheScreen() {
        checkoutOverviewPage.scrollToBottom();
        System.out.println("Scrolled down to the bottom of the screen");
    }

    @When("I tap the Finish button")
    public void iTapTheFinishButton() {
        checkoutOverviewPage.clickFinish();
        System.out.println("Tapped Finish button");
    }

    @Then("I should be on the Checkout Complete screen")
    public void iShouldBeOnTheCheckoutCompleteScreen() {
        Assert.assertTrue("Checkout Complete screen should be displayed",
                checkoutCompletePage.isCheckoutCompleteScreenDisplayed());
        System.out.println("Navigated to Checkout Complete screen");
    }

    @When("I tap the Back Home button")
    public void iTapTheBackHomeButton() {
        checkoutCompletePage.clickBackHome();
        System.out.println("Tapped Back Home button");
    }

    @Then("I should be on the Inventory screen")
    public void iShouldBeOnTheInventoryScreen() {
        Assert.assertTrue("Inventory screen should be displayed",
                inventoryPage.isInventoryScreenDisplayed());
        System.out.println("Navigated to Inventory screen");
    }
}
