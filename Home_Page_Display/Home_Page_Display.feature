Feature: Home Page Display
  As a visitor,
  I want to access the home page,
  So that I can view banners, events, and navigation options.

  Scenario: Successful home page load
    Given the user navigates to https://in.bookmyshow.com/
    Then the home page should be displayed with banners, events, and navigation options
