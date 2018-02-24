package test;

import main.RouterHTTP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Tests routerHTTP methods.
 */
public class sendRequestSampleTest {

    RouterHTTP router = new RouterHTTP();
    String line, line2 ;


    /**
     * Creates data for testing.
     * @throws IOException
     */

    @Before

    public void CreateData() throws IOException {

        InputStream stream =  router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/a/2017-12-11/?format=json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer sb = new StringBuffer();

        while((line = reader.readLine()) != null) sb.append(line);
        line = sb.toString();


        InputStream stream2 = router.sendRequest("http://api.nbp.pl/api/cenyzlota/2017-12-11/?format=json");
        reader = new BufferedReader(new InputStreamReader(stream2));

        StringBuffer sb2 = new StringBuffer();
        while((line2 = reader.readLine()) != null) sb2.append(line2);
        line2 = sb2.toString();


    }

    /**
     * tests sendRequest method.
     */
    @Test
    public void sendRequestTest(){


        Assert.assertEquals(line, "[{\"table\":\"A\",\"no\":\"239/A/NBP/2017\",\"effectiveDate\":\"2017-12-11\",\"rates\":[{\"currency\":\"bat (Tajlandia)\",\"code\":\"THB\",\"mid\":0.1092},{\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"mid\":3.5633},{\"currency\":\"dolar australijski\",\"code\":\"AUD\",\"mid\":2.6840},{\"currency\":\"dolar Hongkongu\",\"code\":\"HKD\",\"mid\":0.4564},{\"currency\":\"dolar kanadyjski\",\"code\":\"CAD\",\"mid\":2.7743},{\"currency\":\"dolar nowozelandzki\",\"code\":\"NZD\",\"mid\":2.4688},{\"currency\":\"dolar singapurski\",\"code\":\"SGD\",\"mid\":2.6380},{\"currency\":\"euro\",\"code\":\"EUR\",\"mid\":4.2038},{\"currency\":\"forint (Węgry)\",\"code\":\"HUF\",\"mid\":0.013374},{\"currency\":\"frank szwajcarski\",\"code\":\"CHF\",\"mid\":3.5965},{\"currency\":\"funt szterling\",\"code\":\"GBP\",\"mid\":4.7646},{\"currency\":\"hrywna (Ukraina)\",\"code\":\"UAH\",\"mid\":0.1312},{\"currency\":\"jen (Japonia)\",\"code\":\"JPY\",\"mid\":0.031431},{\"currency\":\"korona czeska\",\"code\":\"CZK\",\"mid\":0.1643},{\"currency\":\"korona duńska\",\"code\":\"DKK\",\"mid\":0.5648},{\"currency\":\"korona islandzka\",\"code\":\"ISK\",\"mid\":0.034233},{\"currency\":\"korona norweska\",\"code\":\"NOK\",\"mid\":0.4250},{\"currency\":\"korona szwedzka\",\"code\":\"SEK\",\"mid\":0.4194},{\"currency\":\"kuna (Chorwacja)\",\"code\":\"HRK\",\"mid\":0.5571},{\"currency\":\"lej rumuński\",\"code\":\"RON\",\"mid\":0.9075},{\"currency\":\"lew (Bułgaria)\",\"code\":\"BGN\",\"mid\":2.1494},{\"currency\":\"lira turecka\",\"code\":\"TRY\",\"mid\":0.9303},{\"currency\":\"nowy izraelski szekel\",\"code\":\"ILS\",\"mid\":1.0127},{\"currency\":\"peso chilijskie\",\"code\":\"CLP\",\"mid\":0.005435},{\"currency\":\"peso filipińskie\",\"code\":\"PHP\",\"mid\":0.0708},{\"currency\":\"peso meksykańskie\",\"code\":\"MXN\",\"mid\":0.1879},{\"currency\":\"rand (Republika Południowej Afryki)\",\"code\":\"ZAR\",\"mid\":0.2609},{\"currency\":\"real (Brazylia)\",\"code\":\"BRL\",\"mid\":1.0824},{\"currency\":\"ringgit (Malezja)\",\"code\":\"MYR\",\"mid\":0.8741},{\"currency\":\"rubel rosyjski\",\"code\":\"RUB\",\"mid\":0.0602},{\"currency\":\"rupia indonezyjska\",\"code\":\"IDR\",\"mid\":0.00026293},{\"currency\":\"rupia indyjska\",\"code\":\"INR\",\"mid\":0.055375},{\"currency\":\"won południowokoreański\",\"code\":\"KRW\",\"mid\":0.003264},{\"currency\":\"yuan renminbi (Chiny)\",\"code\":\"CNY\",\"mid\":0.5384},{\"currency\":\"SDR (MFW)\",\"code\":\"XDR\",\"mid\":5.0497}]}]");

        Assert.assertEquals(line2, "[{\"data\":\"2017-12-11\",\"cena\":143.98}]");



    }




}
