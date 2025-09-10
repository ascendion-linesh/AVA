Feature: Movie/Event Details
  As a user,
  I want to view details of a movie or event,
  So that I can make informed booking decisions.

  Scenario: View movie or event details
    Given the user has searched for a movie or event
    When the user clicks on a movie or event from the search results
    Then the details page for the selected movie or event should be displayed with information and booking options
