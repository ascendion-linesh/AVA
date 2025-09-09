Feature: Logout Functionality
  As a logged-in user,
  I want to log out of my account,
  So that I can end my session securely.

  Scenario: Log out from the application
    Given the user is logged in
    When the user clicks on profile or account
    And clicks 'Logout'
    Then the user should be logged out and redirected to the home page
