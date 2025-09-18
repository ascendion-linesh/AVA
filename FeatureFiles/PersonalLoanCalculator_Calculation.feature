Feature: Personal Loan Calculator - Calculation
  As a prospective borrower,
  I want to calculate my monthly payment, total payment, and total interest,
  So that I can make informed financial decisions.

  Scenario: Calculate monthly payment with valid data
    Given the user is on the personal loan calculator page
    When the user enters "10000" as the loan amount
    And enters "5" as the interest rate
    And enters "5" as the loan term
    And enters "100" as additional fees
    And clicks the "Calculate" button
    Then the monthly payment should be calculated and displayed correctly

  Scenario: Calculate total payment with valid data
    Given the user is on the personal loan calculator page
    When the user enters "10000" as the loan amount
    And enters "5" as the interest rate
    And enters "5" as the loan term
    And enters "100" as additional fees
    And clicks the "Calculate" button
    Then the total payment should be calculated and displayed correctly

  Scenario: Calculate total interest with valid data
    Given the user is on the personal loan calculator page
    When the user enters "10000" as the loan amount
    And enters "5" as the interest rate
    And enters "5" as the loan term
    And enters "100" as additional fees
    And clicks the "Calculate" button
    Then the total interest should be calculated and displayed correctly

  Scenario: Calculation with zero additional fees
    Given the user is on the personal loan calculator page
    When the user enters "10000" as the loan amount
    And enters "5" as the interest rate
    And enters "5" as the loan term
    And enters "0" as additional fees
    And clicks the "Calculate" button
    Then the calculation should be performed and total payment and interest should reflect zero fees

  Scenario: Calculation with large values
    Given the user is on the personal loan calculator page
    When the user enters "1000000" as the loan amount
    And enters "20" as the interest rate
    And enters "40" as the loan term
    And enters "10000" as additional fees
    And clicks the "Calculate" button
    Then the calculation should be performed and results displayed or an error shown if out of range
