Feature: Booking Confirmation Notification
  As a user,
  I want to receive a confirmation after booking,
  So that I know my booking is successful.

  Scenario: Receive booking confirmation via email or SMS
    Given the booking is successful
    When the user completes a ticket booking
    Then a booking confirmation should be received via email or SMS
