Feature: Search Functionality
  As a user,
  I want to search for movies or events,
  So that I can find relevant options.

  Scenario: Search for movies or events
    Given the user is on the home page
    When the user enters a movie or event name in the search bar
    And clicks 'Search'
    Then relevant results should be displayed
