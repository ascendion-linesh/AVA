Feature: User Session Management
  As a logged-in user,
  I want to log out of my account,
  So that my session is secure.

  Scenario: Logout from the application
    Given the user is logged in
    When the user clicks on the profile icon
    And clicks 'Logout'
    Then the user should be logged out
    And redirected to the homepage
