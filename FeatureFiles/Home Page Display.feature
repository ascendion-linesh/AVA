Feature: Home Page Display
  As a user,
  I want to access the home page,
  So that I can view available movies and events.

  Scenario: Home page loads successfully
    Given the user navigates to https://in.bookmyshow.com/
    Then the home page should display banners, a search bar, and navigation menus
