package main;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Present parsed and computed json data to the User.
 */
public interface IJsonDataPresenter {



    /**
     * Presents current gold price.
     * @param jsonArray JsonArray
     */

     void presentGoldPrice(JsonArray jsonArray);

    /**
     * Presents exchange rate of defined currency.
     * @param jsonObject JsonObject
     */
     void presentExchangeRate(JsonObject jsonObject);

    /**
     * Presents average gold price in defined period of time
     * @param averagePrice BigDecimal given from method "computeAverageGoldPrice".
     */
    void presentAverageGoldPrice(BigDecimal averagePrice);

    /**
     * Presents currency with the lowest bid rate.
     * @param currency String
     */
     void presentCheapestCurency(String currency);

    /**
     * Presents information about currency: its highest and lowest price between defined period of time.
     * @param information
     */
     void presentHighestAndLowestPriceOfCurrency(Object [] information);

    /**
     * Presents information about  currency with the highest amplitude of exchange rate.
     * @param information
     */
    void presentCurrencyWithHighestAmplitude(Object [] information);

    /**
     * Presents sorted table of currencies (sorted by the amplitude between ask rate and bid rate.
     * @param currencies
     */
    void presentSortedTableOfCurrencies(List<JsonNode> currencies);


    /**
     * Prints information about error connected with wrong start date.
     */
    void presentIncorrectStartDateError();

    /**
     * Presents diagram of currencies for every day in given period.
     * @param currencyData
     */
    void presentDiagram(HashMap<String,List<Double>> currencyData, String sign);

    /**
     * Shows description of functions.
     */
    void presentHelp();

}
