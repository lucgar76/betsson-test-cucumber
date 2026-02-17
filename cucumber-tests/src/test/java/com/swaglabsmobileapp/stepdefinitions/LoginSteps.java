package com.swaglabsmobileapp.stepdefinitions;

import com.swaglabsmobileapp.pages.InventoryPage;
import com.swaglabsmobileapp.pages.LoginPage;
import com.swaglabsmobileapp.pages.MenuPage;
import com.swaglabsmobileapp.utils.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

/**
 * Step Definitions for Login Feature
 * Maps Gherkin steps to Java code using Page Objects
 */
public class LoginSteps {
    
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private MenuPage menuPage;
    
    /**
     * Initialize page objects
     */
    public LoginSteps() {
        this.loginPage = new LoginPage(DriverManager.getDriver());
        this.inventoryPage = new InventoryPage(DriverManager.getDriver());
        this.menuPage = new MenuPage(DriverManager.getDriver());
    }
    
    @Given("the app is launched")
    public void theAppIsLaunched() {
        // App is already launched by Hooks - this step is for BDD readability
        // Wait longer for app to fully initialize and reach login screen
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("App launch completed, waiting for initial screen...");
    }
    
    @Given("I am on the login screen")
    public void iAmOnTheLoginScreen() {
        System.out.println("Verifying that user is on the login screen...");
        
        // First, check if we're already on the login screen
        boolean isOnLoginScreen = loginPage.isLoginScreenDisplayed();
        
        if (!isOnLoginScreen) {
            System.out.println("Not on login screen, checking if on inventory screen...");
            
            // Check if we're on the inventory screen instead (from previous test)
            if (inventoryPage.isInventoryScreenDisplayed()) {
                System.out.println("App is on inventory screen, navigating back to login...");
                try {
                    // Click burger menu and logout to get back to login
                    inventoryPage.clickBurgerMenu();
                    Thread.sleep(1000);
                    menuPage.clickLogout();
                    Thread.sleep(2000);
                    
                    // Check again if on login screen
                    isOnLoginScreen = loginPage.isLoginScreenDisplayed();
                    System.out.println("After logout, on login screen: " + isOnLoginScreen);
                } catch (Exception e) {
                    System.err.println("Failed to navigate back to login screen: " + e.getMessage());
                }
            } else {
                System.err.println("App is neither on login nor inventory screen!");
                System.err.println("Waiting additional time for app to load...");
                try {
                    Thread.sleep(3000);
                    isOnLoginScreen = loginPage.isLoginScreenDisplayed();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        if (!isOnLoginScreen) {
            System.err.println("FAILURE: App is NOT on the login screen!");
            System.err.println("This might be due to:");
            System.err.println("1. App is still loading/showing splash screen");
            System.err.println("2. App did not reset properly between tests");
            System.err.println("3. Previous test left app in a different state");
        }
        
        Assert.assertTrue("Login screen should be displayed. App might not have reset properly or is still loading.", 
                         isOnLoginScreen);
    }
    
    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }
    
    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }
    
    @When("I tap the login button")
    public void iTapTheLoginButton() {
        loginPage.clickLoginButton();
    }
    
    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }
    
    @Then("I should be navigated to the inventory screen")
    public void iShouldBeNavigatedToTheInventoryScreen() {
        // Wait for navigation to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue("Inventory screen should be displayed", 
                         inventoryPage.isInventoryScreenDisplayed());
    }
    
    @Then("I should see the products list")
    public void iShouldSeeTheProductsList() {
        Assert.assertTrue("Products should be displayed", 
                         inventoryPage.areProductsDisplayed());
        int productCount = inventoryPage.getProductCount();
        Assert.assertTrue("Product count should be greater than 0", 
                         productCount > 0);
    }
    
    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        Assert.assertTrue("Error message should be displayed", 
                         loginPage.waitForError(5));
        Assert.assertTrue("Error container should be visible", 
                         loginPage.isErrorDisplayed());
    }
    
    @Then("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedErrorText) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(
            String.format("Error message should contain '%s' but was '%s'", 
                         expectedErrorText, actualError),
            loginPage.errorMessageContains(expectedErrorText)
        );
    }
    
    @Then("I should remain on the login screen")
    public void iShouldRemainOnTheLoginScreen() {
        // Verify user is still on login screen
        Assert.assertTrue("User should remain on login screen", 
                         loginPage.isLoginScreenDisplayed());
        // No need to check NOT on inventory - if on login screen, user is not on inventory
    }
    
    @And("I should see the username field")
    public void iShouldSeeTheUsernameField() {
        Assert.assertTrue("Username field should be visible", 
                         loginPage.isUsernameFieldDisplayed());
    }
    
    @And("I should see the password field")
    public void iShouldSeeThePasswordField() {
        Assert.assertTrue("Password field should be visible", 
                         loginPage.isPasswordFieldDisplayed());
    }
    
    @And("I should see the login button")
    public void iShouldSeeTheLoginButton() {
        Assert.assertTrue("Login button should be visible", 
                         loginPage.isLoginButtonDisplayed());
    }
    
    @And("I click on the burger menu at the top left")
    public void iClickOnTheBurgerMenuAtTheTopLeft() {
        inventoryPage.clickBurgerMenu();
        System.out.println("Clicked on burger menu");
        // Wait a moment for menu to open
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @And("I click on the Log out button")
    public void iClickOnTheLogOutButton() {
        menuPage.clickLogout();
        System.out.println("Clicked on Log out button");
        // Wait a moment for logout to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Then("I should be navigated to the login screen")
    public void iShouldBeNavigatedToTheLoginScreen() {
        // Wait for navigation to complete
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assert.assertTrue("Login screen should be displayed after logout", 
                         loginPage.isLoginScreenDisplayed());
        System.out.println("Navigated back to login screen");
    }
}
