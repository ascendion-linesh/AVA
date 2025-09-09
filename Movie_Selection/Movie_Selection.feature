Feature: Movie Selection
  As a user,
  I want to select a movie from the search results,
  So that I can view its details.

  Scenario: Select a movie from search results
    Given the user has searched for a movie
    When the user clicks on the movie from the results
    Then the movie details page should be displayed
