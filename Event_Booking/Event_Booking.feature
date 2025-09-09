Feature: Event Booking
  As a logged-in user,
  I want to book tickets for an event,
  So that I can attend the event.

  Scenario: Book tickets for an event
    Given the user is logged in
    When the user searches for an event
    And selects the event
    And clicks 'Book Tickets'
    And selects date, time, and seats
    And proceeds to payment
    Then the booking summary should be displayed and the user should be redirected to payment
