Feature: Booking History
  As a logged-in user,
  I want to view my booking history,
  So that I can see details of my previous bookings.

  Scenario: View booking history
    Given the user is logged in and has previous bookings
    When the user navigates to 'My Bookings'
    Then the booking history should be displayed with details
