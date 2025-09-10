Feature: Support and Contact
  As a user,
  I want to access the support page,
  So that I can contact customer support when needed.

  Scenario: Access contact support page
    Given the user is on the homepage
    When the user scrolls to the footer
    And clicks on 'Contact Support'
    Then the support page should open with contact options
