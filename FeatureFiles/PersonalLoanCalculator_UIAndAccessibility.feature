Feature: Personal Loan Calculator - UI and Accessibility
  As a user,
  I want the calculator interface to be user-friendly and accessible,
  So that I can easily enter data and understand errors.

  Scenario: Reset or clear button clears all fields and results
    Given the user is on the personal loan calculator page
    When the user enters values in all input fields
    And clicks the "Reset" or "Clear" button
    Then all input fields and result fields should be cleared

  Scenario: Error messages are displayed for invalid inputs
    Given the user is on the personal loan calculator page
    When the user enters invalid data in any field
    And clicks the "Calculate" button
    Then an appropriate error message should be displayed near the field

  Scenario: Error messages disappear when input is corrected
    Given the user is on the personal loan calculator page
    When the user enters invalid data to trigger an error
    And corrects the input
    And clicks the "Calculate" button
    Then the error message should disappear and the calculation should proceed

  Scenario: Fields with errors are visually highlighted
    Given the user is on the personal loan calculator page
    When the user enters invalid data in a field
    And clicks the "Calculate" button
    Then the field should be highlighted to indicate an error

  Scenario: Tab order and accessibility of fields
    Given the user is on the personal loan calculator page
    When the user uses the Tab key to navigate through fields
    Then the focus should move in logical order and all fields should be accessible
