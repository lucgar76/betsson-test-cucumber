@product-navigation
Feature: Product Navigation
  As a logged-in user of the SwagLabs mobile app
  I want to be able to view product details
  So that I can see more information about products I'm interested in

  Background:
    Given the app is launched
    And I am on the login screen
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should be navigated to the inventory screen
    And I should see the products list

  @product @view-toggle
  Scenario: Complete product navigation flow
    When I click on product 1
    Then I should see the product details screen
    When I tap the Back to Products button
    Then I should be navigated to the inventory screen
    And I should see the products list
    When I tap on the view toggle button
    Then I should see the products in list view
    When I scroll down to the bottom of the page
    And I click on the burger menu at the top left
    And I click on the Log out button
