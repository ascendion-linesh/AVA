Feature: User Logout
  As a logged-in user,
  I want to log out of my account,
  So that I can secure my personal information.

  Scenario: Logout from the application
    Given the user is logged in
    When the user clicks on the user profile
    And clicks on 'Logout'
    Then the user should be logged out and redirected to the home or login page
