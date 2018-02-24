package test;


import main.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Checks if we get the proper result from the begining to the end.
 */
public class IntegrationTest {

    IRouterHTTP router = new RouterHTTP();
    IJsonParser parser = new JsonParser();
    IJsonDataManager manager = new JsonDataManager();

    List<JsonNode> information;

    /**
     * Download needed data using avery class involved in the process
     * @throws IOException
     */
    @Before
    public void CreateData() throws IOException {

    information = manager.sortCurrencies(
                               parser.parseJsonDataToArray(
                                       router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/c/2017-12-12/?format=json")));

    }


    /**
     * Compare downloaded and parsed data with expected answer.
     */
    @Test
    public void IntegrationTestForSortingCurrencies(){

        Assert.assertEquals("HUF",information.get(0).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.013505),  java.util.Optional.of(information.get(0).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.013237), java.util.Optional.of(information.get(0).getBid()));

        Assert.assertEquals("JPY",information.get(1).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.031735), java.util.Optional.ofNullable(information.get(1).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.031107), java.util.Optional.ofNullable(information.get(1).getBid()));

        Assert.assertEquals("CZK",information.get(2).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.1658), java.util.Optional.ofNullable(information.get(2).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.1626), java.util.Optional.ofNullable(information.get(2).getBid()));

        Assert.assertEquals("SEK",information.get(3).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.4245), java.util.Optional.ofNullable(information.get(3).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.4161), java.util.Optional.ofNullable(information.get(3).getBid()));

        Assert.assertEquals("NOK",information.get(4).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.4302), java.util.Optional.ofNullable(information.get(4).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.4216), java.util.Optional.ofNullable(information.get(4).getBid()));


        Assert.assertEquals("DKK",information.get(5).getCurrency());
        Assert.assertEquals(java.util.Optional.of(0.5704), java.util.Optional.ofNullable(information.get(5).getAsk()));
        Assert.assertEquals(java.util.Optional.of(0.5592), java.util.Optional.ofNullable(information.get(5).getBid()));


        Assert.assertEquals("AUD",information.get(6).getCurrency());
        Assert.assertEquals(java.util.Optional.of(2.7108), java.util.Optional.ofNullable(information.get(6).getAsk()));
        Assert.assertEquals(java.util.Optional.of(2.6572), java.util.Optional.ofNullable(information.get(6).getBid()));

        Assert.assertEquals("CAD",information.get(7).getCurrency());
        Assert.assertEquals(java.util.Optional.of(2.799), java.util.Optional.ofNullable(information.get(7).getAsk()));
        Assert.assertEquals(java.util.Optional.of(2.7436), java.util.Optional.ofNullable(information.get(7).getBid()));

        Assert.assertEquals("USD",information.get(8).getCurrency());
        Assert.assertEquals(java.util.Optional.of(3.5994), java.util.Optional.ofNullable(information.get(8).getAsk()));
        Assert.assertEquals(java.util.Optional.of(3.5282), java.util.Optional.ofNullable(information.get(8).getBid()));

        Assert.assertEquals("CHF",information.get(9).getCurrency());
        Assert.assertEquals(java.util.Optional.of(3.6315), java.util.Optional.ofNullable(information.get(9).getAsk()));
        Assert.assertEquals(java.util.Optional.of(3.5595), java.util.Optional.ofNullable(information.get(9).getBid()));

        Assert.assertEquals("EUR",information.get(10).getCurrency());
        Assert.assertEquals(java.util.Optional.of(4.2453), java.util.Optional.ofNullable(information.get(10).getAsk()));
        Assert.assertEquals(java.util.Optional.of(4.1613), java.util.Optional.ofNullable(information.get(10).getBid()));

        Assert.assertEquals("GBP",information.get(11).getCurrency());
        Assert.assertEquals(java.util.Optional.of(4.8076), java.util.Optional.ofNullable(information.get(11).getAsk()));
        Assert.assertEquals(java.util.Optional.of(4.7124), java.util.Optional.ofNullable(information.get(11).getBid()));

        Assert.assertEquals("XDR",information.get(12).getCurrency());
        Assert.assertEquals(java.util.Optional.of(5.0996), java.util.Optional.ofNullable(information.get(12).getAsk()));
        Assert.assertEquals(java.util.Optional.of(4.9986), java.util.Optional.ofNullable(information.get(12).getBid()));








    }







}
