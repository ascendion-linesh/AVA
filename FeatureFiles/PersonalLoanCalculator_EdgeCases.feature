Feature: Personal Loan Calculator - Edge Cases
  As a user,
  I want the calculator to handle edge cases gracefully,
  So that I receive clear feedback and robust performance.

  Scenario: All fields left blank
    Given the user is on the personal loan calculator page
    When the user leaves all fields blank
    And clicks the "Calculate" button
    Then error messages for all required fields should be displayed

  Scenario: Multiple invalid fields
    Given the user is on the personal loan calculator page
    When the user enters invalid data in multiple fields
    And clicks the "Calculate" button
    Then all relevant error messages should be displayed

  Scenario: Special characters in numeric fields
    Given the user is on the personal loan calculator page
    When the user enters "!@#$" in numeric fields
    And clicks the "Calculate" button
    Then an error message "Please enter a valid number" should be displayed

  Scenario: Decimal values in integer-only fields
    Given the user is on the personal loan calculator page
    When the user enters "1000.50" in the loan amount or loan term field
    And clicks the "Calculate" button
    Then an error should be displayed or the value should be rounded as per requirements
