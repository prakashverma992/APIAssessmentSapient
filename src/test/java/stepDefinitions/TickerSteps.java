package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import resources.pojo.TickerAPI;
import utilities.APIHelper;
import utilities.JavaUtil;

public class TickerSteps {

    public Response response;
    public String randomObjectSymbol;
    public TickerAPI[] tickerResponse;

    @When("User hits the GET call endpoint {string} on base URL {string}")
    public Response makesCall(String endpoint, String baseURL) {
        String baseURLValue = JavaUtil.readProperties(baseURL);
        String endpointValue = JavaUtil.readProperties(endpoint);

        response = APIHelper.getAPICall(endpointValue, baseURLValue);

        return response;
    }

    @Then("Verify the above response status code as {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actual = response.getStatusCode();
        Assert.assertEquals(actual, expectedStatusCode);
    }

    @Then("User gets random {string} attribute from the above response")
    public String saveRandomAttribute(String string) {
        tickerResponse = response.as(TickerAPI[].class);
        int size = tickerResponse.length;

        for (int i = 0; i < size; i++) {
            int randomIndex = JavaUtil.getRandomIndex(size);
            randomObjectSymbol = tickerResponse[randomIndex].getSymbol();
        }

        return randomObjectSymbol;
    }

    @When("User hits the GET call endpoint {string} with query param on base URL {string}")
    public Response makesCallWithAttributes(String endpoint, String baseURL) {
        String baseURLValue = JavaUtil.readProperties(baseURL);
        String endpointValue = JavaUtil.readProperties(endpoint);
        String updatedEndpointWithQuery = JavaUtil.replaceQueryWithString(endpointValue, randomObjectSymbol);

        response = APIHelper.getAPICall(updatedEndpointWithQuery, baseURLValue);

        return response;
    }

    @Then("User verifies the {string} attribute of json object index {int} from the above all ticker response")
    public void allTickerResponseValidation(String symbol, int index) {
        tickerResponse = response.as(TickerAPI[].class);
        String actualValue = tickerResponse[index].getSymbol();

        Assert.assertEquals(actualValue, symbol);
    }

    @Then("User verifies some attributes and {string} from the above get ticker API response")
    public void tickerResponseValidation(String symbolRes) {
        JsonPath js = APIHelper.readStringToJSON(response.asString());
        String baseAsset = js.getString("symbols[0].baseAsset");
        String quoteAsset = js.getString("symbols[0].quoteAsset");
        String symbol = js.getString("symbols[0].symbol");
        Assert.assertEquals(symbol, baseAsset + quoteAsset);
        Assert.assertTrue(symbol.equalsIgnoreCase(baseAsset + quoteAsset));

        Assert.assertEquals(js.getString("timezone"), symbolRes);

        System.out.println("\n========================== OUTPUT ==========================");
        System.out.println("\nRandom Symbol : " + randomObjectSymbol);
        System.out.println("baseAsset : " + baseAsset);
        System.out.println("quoteAsset : " + quoteAsset);
        System.out.println("symbol validation : " + symbol);
        System.out.println();
        System.out.println("========================== END ==========================\n");
    }

    @Then("Verify count of JSON objects as {int} where symbol contains {string} and {string} is above zero")
    public void verifyCount(int count, String symbol, String firstId) {
        tickerResponse = response.as(TickerAPI[].class);
        int counter = 0;
        for (int i = 0; i < tickerResponse.length; i++) {
            if (tickerResponse[i].getSymbol().contains("XRP") && (tickerResponse[i].getFirstId() > 0)) {
                counter++;
            }
        }

        System.out.println("\n========================== OUTPUT ==========================");
        System.out.println("\nCount : " + counter);
        if (count > 0) {
            System.out.println(
                    "\nPassed !!! \nCount is above ZERO where JSON Object starting with symbol XRP & firstID is above 0\n");
        } else {
            System.out.println("\nFailed !!! \nCount is ZERO or below zero\n");
        }
        System.out.println("========================== END ==========================\n");

    }

}
