Feature: Payment Processing
  As a user of BookMyShow,
  I want to pay for my bookings using various payment methods,
  So that I can complete my ticket purchases securely.

  Scenario: Successful Payment with Card
    Given the user has selected seats
    When the user proceeds to payment
    And enters valid card details
    And confirms the payment
    Then the payment is successful
    And booking confirmation is shown

  Scenario: Payment Failure with Invalid Card
    Given the user has selected seats
    When the user proceeds to payment
    And enters invalid card details
    And confirms the payment
    Then the payment fails
    And an error message is shown

  Scenario: Payment via Wallet
    Given the user has wallet balance
    And has selected seats
    When the user selects wallet payment
    And confirms the payment
    Then the payment is successful
    And booking confirmation is shown
