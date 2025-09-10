Feature: User Login
  As a registered user,
  I want to log in to my account,
  So that I can access personalized features.

  Scenario: Login with valid credentials
    Given the user has an account
    And the user is on the login page
    When the user enters a valid email or mobile number and password
    And clicks on the login button
    Then the user should be logged in and redirected to the dashboard or home page

  Scenario: Login with invalid credentials
    Given the user is on the login page
    When the user enters an invalid email or mobile number or password
    And clicks on the login button
    Then an error message should be displayed
    And the user should not be logged in
