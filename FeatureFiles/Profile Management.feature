Feature: Profile Management
  As a logged-in user,
  I want to update my profile information,
  So that my account details are always up to date.

  Scenario: Update profile information
    Given the user is logged in
    When the user goes to 'My Account'
    And edits profile details
    And saves the changes
    Then the profile should be updated
    And a success message should be displayed
