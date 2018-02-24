package main;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;

import java.util.*;

/**
 * implements computing json data.
 */

public class JsonDataManager implements IJsonDataManager {


    IRouterHTTP router = new RouterHTTP();
    IJsonParser parser = new JsonParser();
    IJsonDataPresenter presenter = new JsonDataPresenter();



    /**
     * Computes json data from json structures.
     * @param startDate, endDate:  period of time to compute average price.
     * @return BigDecimal
     */
    public BigDecimal computeAverageGoldPrice(String startDate, String endDate) throws IOException {

        double averagePrice = 0.0, numberOfDays =0.0;
        JsonArray jsonArray;

        DateTime startDateValue = new DateTime(startDate);
        DateTime endDateValue = new DateTime(endDate);


        // the period is greater than one year
        while (Days.daysBetween(startDateValue, endDateValue).getDays() >= 0) {

            if (Days.daysBetween(startDateValue, endDateValue).getDays() > 365) {
                jsonArray = parser.parseJsonDataToArray(
                        router.sendRequest("http://api.nbp.pl/api/cenyzlota/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue.plusDays(365)) + "/?format=json"));


            }else{
            // the period is less than one year
            jsonArray = parser.parseJsonDataToArray(
                    router.sendRequest("http://api.nbp.pl/api/cenyzlota/" +
                            DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                            DateTimeFormat.forPattern("yyyy-MM-dd").print(endDateValue) + "/?format=json"));
        }

            for (JsonValue value : jsonArray) {

                averagePrice += value.asJsonObject().getJsonNumber("cena").doubleValue() ;
            }

            numberOfDays += jsonArray.size();
            startDateValue = startDateValue.plusDays(365);
        }

        averagePrice /= numberOfDays;

        return new BigDecimal(averagePrice).round(new MathContext(5));

    }




    /**
     * finds currency with the lowest buying rate in defined day.
     * @param jsonArray JsonArray
     * @return currency and bid rate
     */
    public String findCheapestCurrency(JsonArray jsonArray){

        String currency = new String();

        JsonArray array = jsonArray.getJsonObject(0).getJsonArray("rates");
        Double value = array.getJsonObject(0).getJsonNumber("bid").doubleValue();

        for(JsonValue jvalue : array){

           if (jvalue.asJsonObject().getJsonNumber("bid").doubleValue() < value){

               value = jvalue.asJsonObject().getJsonNumber("bid").doubleValue();
               currency = jvalue.asJsonObject().getString("code");

            }
      }

        return (currency+", rate: "+value.toString());
    }


    /**
     * finding lowest and highest rate of the currency.
     * @return String with information
     */
    public Object[] findEdgePricesForCurrency(String currency) throws IOException {

        DateTime startDateValue = new DateTime(2002, 01, 01, 0, 0, 0);
        DateTime endDateValue = new DateTime(new LocalDateTime().toDateTime());
        JsonArray array;

        Double lowest = 100.0, highest = 0.0; // Price of currency
        String highestDate = null, lowestDate = null;



        // we need to divide given period on properly shorter periods in order to get information from server.
        while (Days.daysBetween(startDateValue, endDateValue).getDays() >= 0) {

                if (Days.daysBetween(startDateValue, endDateValue).getDays() > 365) {
                 array = parser.parseJsonDataToObject(
                         router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue.plusDays(365)) + "/?format=json")).
                        getJsonArray("rates");

            }else{
                 array = parser.parseJsonDataToObject(
                         router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(endDateValue) + "/?format=json")).
                        getJsonArray("rates");
            }

            if(array.size() == 1){  //EDGE CASE

              highest = lowest = array.getJsonObject(0).getJsonNumber("mid").doubleValue();
              highestDate = lowestDate = array.getJsonObject(0).getString("effectiveDate");
              break;
            }

            for (int i = 0; i + 1 < array.size(); i++) {

                Double a = array.getJsonObject(i).getJsonNumber("mid").doubleValue();
                Double b = array.getJsonObject(i + 1).getJsonNumber("mid").doubleValue();

                if (a > b) {
                    if (a > highest) {
                        highest = a;
                        highestDate = array.getJsonObject(i).getString("effectiveDate");
                    }

                    if (b < lowest) {
                        lowest = b;
                        lowestDate = array.getJsonObject(i + 1).getString("effectiveDate");
                    }
                } else {
                    if (a < lowest) {
                        lowest = a;
                        lowestDate = array.getJsonObject(i).getString("effectiveDate");
                    }
                    if (b > highest) {
                        highest = b;
                        highestDate = array.getJsonObject(i + 1).getString("effectiveDate");
                    }
                }

            }

            startDateValue = startDateValue.plusDays(365);
        }


        return new Object[]{currency,highest,highestDate,lowest,lowestDate};


    }





    /**
     * Finds currency with highest amplitude of exchange rate.
     * @param startDate
     * @return Object [] information.
     */
    public Object[] findCurrencyWithHighestAmplitude(String startDate) throws IOException {

        String currency = null;
        Double amplitude = 0.0;
        HashMap<String,ArrayList<Double>> map = new HashMap<>();
        DateTime startDateValue = new DateTime(startDate);
        DateTime endDateValue = new DateTime(new LocalDateTime().toDateTime());

        JsonArray array;


        while (Days.daysBetween(startDateValue, endDateValue).getDays() >= 0) {

            if (Days.daysBetween(startDateValue, endDateValue).getDays() > 92) {
                array = parser.parseJsonDataToArray(
                        router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/a/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue.plusDays(92)) + "/?format=json"));


            } else {
                array = parser.parseJsonDataToArray(
                        router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/a/"  +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(endDateValue) + "/?format=json"));
            }

            //Gathering every price of every currency in period, grouped by currency in lists.
            for (int i = 0; i < array.size(); i++) {

                for (JsonValue jValue : array) {

                    JsonArray jarray = jValue.asJsonObject().getJsonArray("rates");

                    for (JsonValue innerJValue : jarray) {

                        if( ! map.containsKey(innerJValue.asJsonObject().getString("code")))
                           map.put(innerJValue.asJsonObject().getString("code"), new ArrayList<>());

                        map.get(innerJValue.asJsonObject().getString("code")).add(innerJValue.asJsonObject().getJsonNumber("mid").doubleValue());

                    }

                }
            }

            startDateValue = startDateValue.plusDays(92);

        }



        //finding amplitude of currencies and  keeping the highest.
        for(Map.Entry<String,ArrayList<Double>> pair : map.entrySet()){

            Double max = Collections.max(pair.getValue());
            Double min = Collections.min(pair.getValue());

            if(max - min > amplitude){
                currency = pair.getKey();
                amplitude = max - min ;
            }

        }

        return new Object[]{currency,amplitude};
    }


    /**
     * sort table A of currencies by the amplitude between ask rate and bid rate.
     * @param jsonArray
     * @return
     */
    public List<JsonNode> sortCurrencies(JsonArray jsonArray){

        JsonArray array = jsonArray.getJsonObject(0).getJsonArray("rates");
        List<JsonNode> currencies = new ArrayList<>();


        for(JsonValue jValue : array){

            currencies.add(new JsonNode(
                jValue.asJsonObject().getString("code"),
                jValue.asJsonObject().getJsonNumber("ask").doubleValue(),
                jValue.asJsonObject().getJsonNumber("bid").doubleValue())
            );
        }


        Collections.sort(currencies, Comparator.comparing(JsonNode::getAmplitude));


        return currencies;
    }

    /**
     * generates data for diagram.
     * @param currency
     * @param startYear
     * @param startMonth
     * @param startWeek
     * @param endYear
     * @param endMonth
     * @param endWeek
     * @return
     * @throws IOException
     */
    public HashMap<String, List<Double>> createDiagram (String currency, String startYear,String startMonth, String startWeek,String endYear, String endMonth, String endWeek) throws IOException {

         HashMap<String, List<Double>> currencyData = new HashMap<>();
         DateTime  startDateValue = new DateTime(startYear + "-" + startMonth + "-01");
         DateTime endDateValue =   new DateTime(endYear + "-" + endMonth + "-01" );

         JsonArray jArray;





        for(int i = 0;i < Integer.parseInt(startWeek);) {

             if (startDateValue.getDayOfWeek() == 1) i++;

             startDateValue = startDateValue.plusDays(1);

         }


        for(int i =0;i < Integer.parseInt(endWeek);) {

            if (endDateValue.getDayOfWeek() == 5) i++;

           endDateValue = endDateValue.plusDays(1);

        }


        while (Days.daysBetween(startDateValue, endDateValue).getDays() >= 0) {

            if (Days.daysBetween(startDateValue, endDateValue).getDays() > 365) {
                jArray = parser.parseJsonDataToObject(
                        router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue.plusDays(365)) + "/?format=json")).
                        getJsonArray("rates");

            } else {
                jArray = parser.parseJsonDataToObject(
                        router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(startDateValue) + "/" +
                                DateTimeFormat.forPattern("yyyy-MM-dd").print(endDateValue) + "/?format=json")).
                        getJsonArray("rates");
            }


            for(int i=0;i<jArray.size();i++){

                DateTime date = new DateTime(jArray.getJsonObject(i).getString("effectiveDate"));
                DateTime.Property weekDay = date.dayOfWeek();

                if(currencyData.containsKey(weekDay.getAsText()) == false)
                currencyData.put(weekDay.getAsText(), new ArrayList<>());

                currencyData.get(weekDay.getAsText()).add(jArray.getJsonObject(i).getJsonNumber("mid").doubleValue());

            }


            startDateValue = startDateValue.plusDays(365);

        }

         return currencyData;
    }



}
