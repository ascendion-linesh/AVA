Feature: Booking Confirmation
  As a user of BookMyShow,
  I want to view confirmation and history of my bookings,
  So that I can be assured of my purchases and track my tickets.

  Scenario: View Booking Confirmation
    Given the payment is successful
    When the user completes the payment
    Then the booking confirmation page is displayed with details

  Scenario: View Past Bookings
    Given the user has previous bookings
    When the user goes to 'My Bookings'
    And views the list
    Then all past bookings are listed
