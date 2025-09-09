Feature: Booking Show and Seats
  As a user of BookMyShow,
  I want to select shows and seats,
  So that I can book tickets for movies conveniently.

  Scenario: Select Show and Seats
    Given the user is logged in
    And the movie is available
    When the user selects a movie
    And chooses a date and time
    And selects seats
    Then the selected seats are highlighted
    And the option to proceed to payment is enabled

  Scenario: Booking with Unavailable Seats
    Given some seats are already booked
    When the user tries to select booked seats
    Then an error or unavailable indication is shown

  Scenario: Modify Seat Selection Before Payment
    Given seats are selected
    When the user deselects seats
    And selects new seats
    Then the new seats are selected
    And the old ones are released
