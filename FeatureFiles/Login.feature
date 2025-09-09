Feature: Login and Registration
  As a user of BookMyShow,
  I want to securely log in, register, and recover my password,
  So that I can access my account and use the platform.

  Scenario: Successful Login
    Given the user has an account
    And the user is on the login page
    When the user enters valid credentials
    And clicks 'Login'
    Then the user is successfully logged in

  Scenario: Invalid Login
    Given the user is on the login page
    When the user enters invalid credentials
    And clicks 'Login'
    Then an error message is displayed

  Scenario: Successful Registration
    Given the user is not registered
    And the user is on the registration page
    When the user enters valid details
    And submits the registration
    Then the user is registered and logged in

  Scenario: Forgot Password Flow
    Given the user has a registered email
    And is on the login page
    When the user clicks 'Forgot Password'
    And enters their email
    And submits the request
    Then a password reset link is sent to the user's email
