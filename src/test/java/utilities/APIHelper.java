package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

public class APIHelper {

    public static Response response;
    private static String apiResponse;

    public static JsonPath readStringToJSON(String payload) {
        JsonPath js = new JsonPath(payload);

        return js;
    }

    public static RequestSpecification requestSpec(String baseURL) {

        return new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

    public static ResponseSpecification responseSpec() {

        return new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
    }

    public static Response getAPICall(String endpoint, String baseURL) {
        response = given().log().all().spec(APIHelper.requestSpec(baseURL)).when().get(endpoint).then()
                .spec(APIHelper.responseSpec()).extract().response();

        return response;
    }

}
