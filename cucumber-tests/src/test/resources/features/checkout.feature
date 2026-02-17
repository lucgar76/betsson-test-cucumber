@checkout
Feature: Checkout Flow
  As a logged-in user of the SwagLabs mobile app
  I want to add products to my cart from the inventory screen
  So that I can proceed to checkout

  Background:
    Given the app is launched
    And I am on the login screen
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should be navigated to the inventory screen
    And I should see the products list

  @checkout @add-to-cart
  Scenario: Add product to cart from inventory
    When I tap on the view toggle button
    And I tap the add to cart button for the product "Sauce Labs Bolt T-Shirt"
    Then the product should be added to the cart
    When I tap on the cart
    Then I should be on the Your Cart screen
    When I tap the Checkout button
    Then I should be on the Checkout Information screen
    When I enter First Name "Johnny"
    And I enter Last Name "Test"
    And I enter Zip/Postal Code "12345"
    And I tap the Continue button
    Then I should be on the Checkout Overview screen
    When I scroll down to the bottom of the screen
    And I tap the Finish button
    Then I should be on the Checkout Complete screen
    When I tap the Back Home button
    Then I should be on the Inventory screen
    And I click on the burger menu at the top left
    And I click on the Log out button
    Then I should be navigated to the login screen
