package test;

import main.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Tests computing and finding methods we use on json structure.
 */
public class JsonDataManagerSampleTests {

    IRouterHTTP router = new RouterHTTP();
    IJsonParser parser = new JsonParser();
    IJsonDataManager manager = new JsonDataManager();

    BigDecimal price;
    String currency;
    Object[] information, information2, information3;


    @Before
    public void  CreateData() throws IOException {


        price = manager.computeAverageGoldPrice("2017-12-01","2017-12-14");


        currency =  manager.findCheapestCurrency(
                parser.parseJsonDataToArray(
                        router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/c/2017-12-07/?format=json")));


         information = new Object[]{"gbp",5.1975,"2017-01-03",4.5852,"2017-08-29"};


         information2 = manager.findEdgePricesForCurrency("gbp");


         information3 = manager.findCurrencyWithHighestAmplitude("2017-10-10");


    }


    @Test
    public void computeAvrageGoldPriceTest() throws IOException {

        Assert.assertEquals(144.26 , price.doubleValue(), 0);

    }


    @Test
    public void findCheapestCurrencyTest(){
        Assert.assertEquals("HUF, rate: 0.013265",currency);
    }



    @Test
    public void findEdgePricesForCurrencyTest(){ Assert.assertEquals(information,information2);}



    @Test
   public void FindCurrencyWithHighestAmplitudeTest(){Assert.assertEquals(new Object[]{"CAD",0.1650999999999998}, information3);}

}

