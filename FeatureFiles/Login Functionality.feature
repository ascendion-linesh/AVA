Feature: Login Functionality
  As a registered user,
  I want to log in to my BookMyShow account,
  So that I can access personalized features.

  Scenario: Successful login with valid credentials
    Given the user has an account
    And the user is on the login page
    When the user enters valid credentials
    And clicks on the 'Login' button
    Then the user should be successfully logged in

  Scenario: Login attempt with invalid credentials
    Given the user is on the login page
    When the user enters invalid credentials
    And clicks on the 'Login' button
    Then an error message should be displayed

  Scenario: Forgot password functionality
    Given the user has an account
    And the user is on the login page
    When the user clicks on 'Forgot Password'
    And enters their registered email
    And submits the request
    Then a password reset email should be sent
