Feature: User Logout
  As a user of BookMyShow,
  I want to log out of my account securely,
  So that my session ends and my account remains safe.

  Scenario: User Logout
    Given the user is logged in
    When the user clicks on the profile
    And clicks 'Logout'
    Then the user is logged out
    And redirected to the homepage
