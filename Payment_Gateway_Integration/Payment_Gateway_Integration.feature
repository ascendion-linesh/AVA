Feature: Payment Gateway Integration
  As a user booking tickets,
  I want to pay for my tickets,
  So that I can complete my booking.

  Scenario: Complete payment for ticket booking
    Given the user has selected tickets and reached the payment page
    When the user chooses a payment method
    And enters payment details
    And confirms payment
    Then the payment should be processed and a confirmation message or ticket should be displayed
