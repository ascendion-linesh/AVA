Feature: Payment Processing
  As a user with tickets in the cart,
  I want to pay using various payment methods,
  So that I can complete my booking.

  Scenario: Pay with a valid card
    Given the user has tickets in the cart
    When the user proceeds to payment
    And selects card payment
    And enters valid card details
    And submits the payment
    Then the payment should be successful
    And the booking confirmation should be shown

  Scenario: Pay with an invalid card
    Given the user has tickets in the cart
    When the user proceeds to payment
    And selects card payment
    And enters invalid card details
    And submits the payment
    Then an error message "Invalid card details" should be displayed

  Scenario: Pay using UPI
    Given the user has tickets in the cart
    When the user proceeds to payment
    And selects the UPI option
    And enters a valid UPI ID
    And approves the payment in the UPI app
    Then the payment should be successful
    And the booking confirmation should be shown
