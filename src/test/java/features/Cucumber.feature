@ApiAssessment
Feature: Validate ticker API

  @Test1
  Scenario Outline: Validate 24 hour all ticker Get API
    When User hits the GET call endpoint "allTickerEndpoint" on base URL "binanceBaseURL"
    Then Verify the above response status code as 200
    And User verifies the "<symbol>" attribute of json object index <index> from the above all ticker response
    And Verify count of JSON objects as 13 where symbol contains "XRP" and "firstId" is above zero
    Examples:
      | symbol | index |
      | ETHBTC | 0     |
      | BNBBTC | 2     |

  @Test2
  Scenario Outline: Validate ticker details Get API
    When User hits the GET call endpoint "allTickerEndpoint" on base URL "binanceBaseURL"
    Then Verify the above response status code as 200
    And User gets random "symbol" attribute from the above response
    When User hits the GET call endpoint "tickerEndpoint" with query param on base URL "binanceBaseURL"
    Then Verify the above response status code as 200
    And User verifies some attributes and "<timezone>" from the above get ticker API response
    Examples:
      | timezone |
      | UTC      |
