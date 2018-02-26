package test;

import main.IJsonParser;
import main.IRouterHTTP;
import main.JsonParser;
import main.RouterHTTP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;

/**
 * Tests Json parser methods.
 */
public class ParseJsonDataSampleTest {



    IRouterHTTP router = new RouterHTTP();
    IJsonParser parser = new JsonParser();
    JsonObject jsonObject1, jsonObject2;
    JsonArray jsonArray1, jsonArray2;


    @Before
    public void createData() throws IOException {

        jsonArray1 = parser.parseJsonDataToArray(
                     router.sendRequest("http://api.nbp.pl/api/cenyzlota/2017-12-08/2017-12-12/?format=json"));


        jsonArray2 = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("data", "2017-12-08")
                        .add("cena", 144.14))
                .add(Json.createObjectBuilder()
                        .add("data", "2017-12-11")
                        .add("cena", 143.98))
                .add(Json.createObjectBuilder()
                        .add("data", "2017-12-12")
                        .add("cena", 142.89))
                .build();


         jsonObject1 = Json.createObjectBuilder()
                .add("table", "A")
                .add("currency", "frank szwajcarski")
                .add("code", "CHF")
                .add("rates", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("no", "240/A/NBP/2017")
                                .add("effectiveDate", "2017-12-12")
                                .add("mid", 3.6085)))
                        .build();


         jsonObject2 = parser.parseJsonDataToObject(
                       router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/chf/2017-12-12/?format=json"));


    }



    @Test
    public void parseJsonDatatoArrayandToObjectTest(){

        Assert.assertEquals(jsonArray1,jsonArray2);

        Assert.assertEquals(jsonObject1,jsonObject1);

    }




}
