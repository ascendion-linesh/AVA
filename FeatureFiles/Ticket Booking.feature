Feature: Ticket Booking
  As a logged-in user,
  I want to book tickets for a movie or event,
  So that I can attend the show.

  Scenario: Book tickets for a movie or event
    Given the user is logged in
    And has searched and selected a movie or event
    When the user clicks on 'Book Tickets'
    And selects the date, time, and seats
    And proceeds to payment
    Then the user should be taken to the payment page after seat selection
