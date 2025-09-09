Feature: City Selection
  As a user,
  I want to select or change my city,
  So that I can see relevant events and movies.

  Scenario: Change selected city
    Given the user is on the home page
    When the user clicks on the city selector
    And chooses a different city
    Then the selected city should be updated and relevant events or movies should be shown
