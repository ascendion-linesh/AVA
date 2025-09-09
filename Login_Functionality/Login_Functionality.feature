Feature: Login Functionality
  As a registered user,
  I want to log in to my account,
  So that I can access my dashboard.

  Scenario: Successful login with valid credentials
    Given the user has a registered account
    When the user clicks 'Sign In'
    And enters a valid email or mobile and password
    And clicks 'Login'
    Then the user should be successfully logged in and redirected to the dashboard

  Scenario: Login with invalid credentials
    Given the user is on the login page
    When the user enters an invalid email or mobile or password
    And clicks 'Login'
    Then an error message should be displayed indicating invalid credentials
