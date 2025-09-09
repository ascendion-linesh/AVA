Feature: Movie Search
  As a user of BookMyShow,
  I want to search for movies and filter them by city,
  So that I can easily find and view details of movies I am interested in.

  Scenario: Search for a Movie by Name
    Given the user is logged in
    When the user enters a movie name in the search bar
    And presses Enter
    Then relevant movie results are displayed

  Scenario: Filter Movies by City
    Given the user is logged in
    When the user selects a city
    Then movies available in the selected city are shown

  Scenario: View Movie Details
    Given the movie is listed in the search results
    When the user clicks on a movie from the list
    Then the movie details page is displayed
