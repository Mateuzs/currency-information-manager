package main;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Computes json data from json structures.
 */
public interface IJsonDataManager {

    /**
     * Computes average gold price in defined period.
     * @param   startDate, endDate : period of time to compute average price.
     * @return BigDecimal, average gold price.
     */
    BigDecimal computeAverageGoldPrice (String startDate, String EndDate) throws IOException;

    /**
     * Finds currency with the lowest buying rate in defined day.
     * @param jsonArray JsonArray
     * @return  String : currency and bid rate.
     */
    String findCheapestCurrency(JsonArray jsonArray);


    /**
     * Finds, when the currency rate was lowest and when highest.
      * @param currency String
     * @return Object[], {currency,highestValue,highestValueDate,lowestValue,lowestValueDay} with information (double and string).
     */
     Object[]  findEdgePricesForCurrency(String currency) throws IOException;


    /**
     * Presents currency with highest amplitude of exchange rate.
     * @param startDate String
     * @return Objext [] information string currency name and double amplitude.
     */
     Object[] findCurrencyWithHighestAmplitude(String startDate) throws IOException;

    /**
     * Sorts table A of currencies by the amplitude between ask rate and bid rate.
     * @param jsonArray JsonArray
     * @return
     */
     List<JsonNode> sortCurrencies(JsonArray jsonArray);

    /**
     * Generate data for currency diagram.
     * @param currency
     * @param startYear
     * @param startMonth
     * @param startWeek
     * @param endYear
     * @param endMonth
     * @param endWeek
     * @return
     */
    HashMap<String,List<Double>> createDiagram (String currency, String startYear,String startMonth, String startWeek,String endYear, String endMonth, String endWeek) throws IOException;



}
