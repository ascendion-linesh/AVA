Feature: Forgot Password
  As a registered user,
  I want to reset my password if I forget it,
  So that I can regain access to my account.

  Scenario: Reset password using forgot password functionality
    Given the user has an account
    And is on the login page
    When the user clicks on 'Forgot Password'
    And enters the registered email or mobile number
    And follows the reset instructions
    Then a password reset email or SMS should be sent
    And the user can reset their password
