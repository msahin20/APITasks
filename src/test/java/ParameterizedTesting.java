import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class ParameterizedTesting {

   @ParameterizedTest
   @CsvSource ({"NY,New York",
                "CO,Denver",
                "VA,Fairfax",
                 "VA,Arlington"               })
    public void test1(String state, String city){
        baseURI= "https://api.zippopotam.us";


        Response response = given().pathParam("state", state).
                accept(ContentType.JSON).
                and().
                pathParam("city", city).
                when().get("/us/{state}/{city}").
                then().statusCode(200).
                and().
                body("places.'place name'",everyItem(containsStringIgnoringCase(city))).
                extract().response();

        //response.prettyPrint();
        JsonPath jsonPath = response.jsonPath();

        List<String> list= jsonPath.getList("places.'place name'");



        for (String each : list) {
            //assertTrue(each.toLowerCase().contains(city.toLowerCase()));

        }

       System.out.println("jsonPath.getList(\"places\").size() = " + jsonPath.getList("places").size());


   }
}
