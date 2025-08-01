Feature: shipping address
    @TestCaseKey=ML-T3
    Scenario: select the department and city of shipment
        
        Given user on the home page
        When user click on the enter location section
        And select state "Antioquia"
        And select a city "Medellín" located in the selected state
        And click on the accept button
        Then the selected city "Medellín" should be displayed in the location section