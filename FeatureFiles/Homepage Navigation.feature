Feature: Homepage Navigation
  As a user,
  I want to navigate carousel images on the homepage,
  So that I can view featured movies and events.

  Scenario: Navigate homepage carousel
    Given the user is on the homepage
    When the user clicks the carousel arrows to navigate
    Then the carousel images should change as expected
