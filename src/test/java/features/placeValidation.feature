Feature: Validating Place API's

  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given AddPlaceAPI payload with name "<name>", language "<language>" and address "<address>"
    When user calls "AddPlaceApi" with "POST" http request
    Then the API call got success with status code 200
    And "status" in response body is "OK"
    And verify place_Id created maps to "<name>" using "GetPlaceApi"

  Examples:
      |name       | language | address         |
      |Some house | English  | Warsaw 12 02-022|
      |Other house| Polish   | Warsaw 14 02-022|