Feature: Personal Loan Calculator - Input Validation
  As a prospective borrower,
  I want to ensure all input fields accept valid data and provide clear feedback,
  So that I can accurately calculate my loan options.

  Scenario: Loan amount field is required
    Given the user is on the personal loan calculator page
    When the user leaves the loan amount field blank
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan amount is required" should be displayed

  Scenario: Loan amount field accepts only numeric values
    Given the user is on the personal loan calculator page
    When the user enters "abc" in the loan amount field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Please enter a valid number" should be displayed

  Scenario: Loan amount field does not accept zero
    Given the user is on the personal loan calculator page
    When the user enters "0" in the loan amount field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan amount must be greater than zero" should be displayed

  Scenario: Loan amount field does not accept negative numbers
    Given the user is on the personal loan calculator page
    When the user enters "-5000" in the loan amount field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan amount must be positive" should be displayed

  Scenario: Loan amount field handles large values
    Given the user is on the personal loan calculator page
    When the user enters "1000000000" in the loan amount field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then the calculation should be performed or an appropriate error message should be displayed

  Scenario: Interest rate field is required
    Given the user is on the personal loan calculator page
    When the user leaves the interest rate field blank
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Interest rate is required" should be displayed

  Scenario: Interest rate field accepts only numeric values
    Given the user is on the personal loan calculator page
    When the user enters "xyz" in the interest rate field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Please enter a valid number" should be displayed

  Scenario: Interest rate field does not accept negative numbers
    Given the user is on the personal loan calculator page
    When the user enters "-5" in the interest rate field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Interest rate must be positive" should be displayed

  Scenario: Interest rate field does not accept zero
    Given the user is on the personal loan calculator page
    When the user enters "0" in the interest rate field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Interest rate must be greater than zero" should be displayed

  Scenario: Interest rate field handles large values
    Given the user is on the personal loan calculator page
    When the user enters "1000" in the interest rate field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then the calculation should be performed or an appropriate error message should be displayed

  Scenario: Loan term field is required
    Given the user is on the personal loan calculator page
    When the user leaves the loan term field blank
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan term is required" should be displayed

  Scenario: Loan term field accepts only numeric values
    Given the user is on the personal loan calculator page
    When the user enters "ten" in the loan term field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Please enter a valid number" should be displayed

  Scenario: Loan term field does not accept negative numbers
    Given the user is on the personal loan calculator page
    When the user enters "-3" in the loan term field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan term must be positive" should be displayed

  Scenario: Loan term field does not accept zero
    Given the user is on the personal loan calculator page
    When the user enters "0" in the loan term field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Loan term must be greater than zero" should be displayed

  Scenario: Loan term field handles large values
    Given the user is on the personal loan calculator page
    When the user enters "1000" in the loan term field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then the calculation should be performed or an appropriate error message should be displayed

  Scenario: Additional fees field accepts only numeric values
    Given the user is on the personal loan calculator page
    When the user enters "fee" in the additional fees field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Please enter a valid number" should be displayed

  Scenario: Additional fees field does not accept negative numbers
    Given the user is on the personal loan calculator page
    When the user enters "-100" in the additional fees field
    And fills in all other required fields
    And clicks the "Calculate" button
    Then an error message "Additional fees must be positive or zero" should be displayed
