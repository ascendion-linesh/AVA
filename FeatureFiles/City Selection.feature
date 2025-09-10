Feature: City Selection
  As a user,
  I want to select my city,
  So that I can view events and movies available in my location.

  Scenario: Select a city
    Given the user is on the home page
    When the user clicks on the city dropdown
    And selects a city from the list
    Then the website should update to show events and movies for the selected city
