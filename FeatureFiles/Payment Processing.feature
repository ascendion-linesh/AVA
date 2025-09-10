Feature: Payment Processing
  As a user,
  I want to pay for my booking,
  So that I can confirm my ticket purchase.

  Scenario: Successful payment with valid card details
    Given the user is on the payment page
    When the user enters valid card details
    And completes the payment
    Then the payment should be successful
    And booking confirmation should be displayed

  Scenario: Failed payment with invalid card details
    Given the user is on the payment page
    When the user enters invalid card details
    And attempts to complete the payment
    Then the payment should fail
    And an error message should be displayed
