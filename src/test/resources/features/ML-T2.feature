Feature: applying filters
    Background:

        Given the user on the home page
        When search product

    @TestCaseKey=ML-T2
    Scenario: applying filters after performing a search

        And select the brand "HP" of the product filter
        Then the first 5 results should contain the brand "HP"
