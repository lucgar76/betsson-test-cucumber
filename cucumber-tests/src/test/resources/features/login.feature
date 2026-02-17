@login
Feature: Login Functionality
  As a user of the SwagLabs mobile app
  I want to be able to log in with my credentials
  So that I can access the app features

  Background:
    Given the app is launched
    And I am on the login screen

  @positive @smoke
  Scenario: Successful login with valid credentials
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should be navigated to the inventory screen
    And I should see the products list
    And I click on the burger menu at the top left
    And I click on the Log out button
    Then I should be navigated to the login screen

  @negative
  Scenario: Login with empty username
    When I enter username ""
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username is required"
    And I should remain on the login screen

  @negative
  Scenario: Login with empty password
    When I enter username "standard_user"
    And I enter password ""
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Password is required"
    And I should remain on the login screen

  @negative
  Scenario: Login with both username and password empty
    When I enter username ""
    And I enter password ""
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username is required"
    And I should remain on the login screen

  @negative
  Scenario: Login with locked out user
    When I enter username "locked_out_user"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Sorry, this user has been locked out."
    And I should remain on the login screen

  @negative
  Scenario: Login with invalid username
    When I enter username "invalid_user"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username and password do not match any user in this service."
    And I should remain on the login screen

  @negative
  Scenario: Login with invalid password
    When I enter username "standard_user"
    And I enter password "invalid_password"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username and password do not match any user in this service."
    And I should remain on the login screen

  @negative
  Scenario: Login with incorrect case in username
    When I enter username "STANDARD_USER"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username and password do not match any user in this service."
    And I should remain on the login screen

  @negative
  Scenario: Login with incorrect case in password
    When I enter username "standard_user"
    And I enter password "SECRET_SAUCE"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username and password do not match any user in this service."
    And I should remain on the login screen

  @negative
  Scenario: Login with special characters in username
    When I enter username "user@#$%"
    And I enter password "secret_sauce"
    And I tap the login button
    Then I should see an error message
    And the error message should contain "Username and password do not match any user in this service."
    And I should remain on the login screen
