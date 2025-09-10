Feature: Movie Ticket Booking
  As a logged-in user,
  I want to book tickets for a movie,
  So that I can watch movies at my preferred cinema.

  Scenario: Book tickets for a movie
    Given the user is logged in
    When the user searches for a movie
    And selects the movie
    And chooses the date, time, and cinema
    And selects seats
    And clicks on 'Proceed to Payment'
    Then the booking summary should be displayed

  Scenario: Attempt to proceed without selecting seats
    Given the user is logged in
    When the user searches for a movie
    And selects the movie
    And chooses the date, time, and cinema
    And does not select any seats
    And clicks on 'Proceed to Payment'
    Then an error message "Please select seats" should be displayed

  Scenario: Cancel booking before payment
    Given the user is logged in
    And a booking is in progress
    When the user initiates a booking
    And clicks 'Cancel' before payment
    Then the booking process should be aborted
