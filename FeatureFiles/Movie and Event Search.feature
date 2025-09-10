Feature: Movie and Event Search
  As a logged-in user,
  I want to search for movies and events,
  So that I can find and book tickets easily.

  Scenario: Search for a movie by name
    Given the user is logged in
    And the user can see the search bar
    When the user enters a movie name
    And presses Enter
    Then a list of matching movies should be displayed

  Scenario: Search for events by location and name
    Given the user is logged in
    When the user selects a location
    And enters an event name
    And presses Enter
    Then a list of events in the selected location should be displayed
