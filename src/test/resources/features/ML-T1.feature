Feature: Products search
    @TestCaseKey=ML-T1
    Scenario: Products search

        Given the user on the home page
        When search product
        Then the user views a list of products related to the search