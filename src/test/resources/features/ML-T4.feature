Feature: select category
    Background:

        Given the user on the home page

    @TestCaseKey=ML-T4
    Scenario: select type category and type of product

        When the user click the category tab
        And hovers over category "Tecnología"
        And select type product of category "Para Xbox"
        Then the selected type product "Para Xbox" should be displayed in the location section