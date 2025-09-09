Feature: Forgot Password
  As a user who forgot their password,
  I want to reset my password,
  So that I can regain access to my account.

  Scenario: Reset forgotten password
    Given the user is on the login page
    When the user clicks 'Forgot Password'
    And enters a registered email or mobile
    And submits the request
    Then a password reset link or OTP should be sent to the user
