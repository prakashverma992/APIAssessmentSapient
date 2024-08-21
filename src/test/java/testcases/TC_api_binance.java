package testcases;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import resources.pojo.TickerAPI;
import utilities.APIHelper;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class TC_api_binance {

    String baseUrl = "https://api.binance.com/api/v3";
    String URIpath;
    TickerAPI[] tickerResponse;

    public TickerAPI[] getAllTickerResponse() {
        RestAssured.baseURI = baseUrl;
        URIpath = "/ticker/24hr";
        tickerResponse = given().log().all().when().get(URIpath).then().spec(APIHelper.responseSpec()).statusCode(200)
                .extract().response().as(TickerAPI[].class);

        return tickerResponse;
    }

    @Test(priority = 0)
    public void TC01() {
        // Test #1
        tickerResponse = getAllTickerResponse();
        int count = 0;
        for (int i = 0; i < tickerResponse.length; i++) {
            if (tickerResponse[i].getSymbol().contains("XRP") && (tickerResponse[i].getFirstId() > 0)) {
                count++;
            }
        }

        System.out.println("\n========================== OUTPUT ==========================");
        System.out.println("\nCount : " + count);
        if (count > 0) {
            System.out.println(
                    "\nPassed !!! \nCount is above ZERO where JSON Object starting with symbol XRP & firstID is above 0\n");
        } else {
            System.out.println("\nFailed !!! \nCount is ZERO or below zero\n");
        }
        System.out.println("========================== END ==========================\n");

    }

    @Test(priority = 1)
    public void TC02() {
        // Test#2
        String randomObjectSymbol = null;

        tickerResponse = getAllTickerResponse();

        int sizeOfArrayResponse = tickerResponse.length;
        for (int i = 0; i < sizeOfArrayResponse; i++) {
            Random random = new Random();
            int randomIndex = random.nextInt(sizeOfArrayResponse);
            randomObjectSymbol = tickerResponse[randomIndex].getSymbol();
            break;
        }

        // Get random symbol data
        RestAssured.baseURI = baseUrl;
        URIpath = "/exchangeInfo";

        String symbolResponse = given().log().all().queryParams("symbol", randomObjectSymbol).when().get(URIpath).then()
                .spec(APIHelper.responseSpec()).statusCode(200).extract().response().asString();

        JsonPath js = APIHelper.readStringToJSON(symbolResponse);
        String baseAsset = js.getString("symbols[0].baseAsset");
        String quoteAsset = js.getString("symbols[0].quoteAsset");
        String symbol = js.getString("symbols[0].symbol");

        Assert.assertEquals(symbol, baseAsset + quoteAsset);

        System.out.println("\n========================== OUTPUT ==========================");
        System.out.println("\nRandom object symbol : " + randomObjectSymbol + "\n");
        System.out.println("baseAsset : " + baseAsset);
        System.out.println("quoteAsset : " + quoteAsset);
        System.out.println("symbol validation : " + symbol);
        System.out.println();
        System.out.println("========================== END ==========================\n");

    }

}
