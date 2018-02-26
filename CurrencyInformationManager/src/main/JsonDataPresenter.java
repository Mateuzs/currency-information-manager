package main;

import org.joda.time.DateTime;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;


/**
 *  Implements methods of presenting json data.
 */
public class JsonDataPresenter implements IJsonDataPresenter {


    public void presentGoldPrice(JsonArray jsonArray){

        JsonObject object = jsonArray.getJsonObject(0);

        String message =" Gold price: " + object.getJsonNumber("cena") + " zł";

        System.out.println(message);

        System.out.println("\n\n\n\n");
    }



    public void presentExchangeRate(JsonObject jsonObject){

        JsonObject object = jsonObject.getJsonArray("rates").getJsonObject(0);

        System.out.println("\n\n Day: "+object.getString("effectiveDate")+ "\n\n Currency: \""+ jsonObject.getString("currency") +"\", price: "+object.getJsonNumber("mid")+" zł");


        System.out.println("\n\n\n\n");

    }



    public void presentAverageGoldPrice(BigDecimal averagePrice){


        System.out.print("\n\nThe average price of gold in defined period : "+ averagePrice+" zł\n");

        System.out.println("\n\n\n\n");


    }



    public void presentCheapestCurency( String currency){

        System.out.println("\n Currency with lowest buying rate: "+currency+"\n");

        System.out.println("\n\n\n\n");

    }



    public void presentHighestAndLowestPriceOfCurrency(Object [] information){

        System.out.println("\nCurrency: "+ information[0] +", highest rate: "+information[1]+", on: "+information[2]+", lowest rate: "+information[3]+", on: "+information[4]+"\n");

        System.out.println("\n\n\n\n");

    }



    public void presentCurrencyWithHighestAmplitude(Object [] information){

        System.out.println("\n Currency with the highest amplitude: "+ information[0]+", amplitude: "+ information[1]+"\n");

        System.out.println("\n\n\n\n");



    }


    public void presentSortedTableOfCurrencies(List<JsonNode> currencies){

        System.out.println("\n Currency  ask-rate  bid-rate \n");

        for(JsonNode node : currencies){

            System.out.println(" "+node.getCurrency()+":     "+node.getAsk()+"   "+node.getBid());
        }

        System.out.println("\n\n\n\n");


    }


    public void presentIncorrectStartDateError(){

        System.out.println("\nDate is beyond the range. Please redefine Your date."+
                "\nDate for currency should be between 2001-01-02 and present day."+
                "\nDate for gold price should be between 2013-01-02 and present day.");

        System.out.println("\n\n\n\n");

    }



    public void presentDiagram(HashMap<String, List<Double>> currencyData, String sign) {

    Double max = currencyData.get("poniedziałek").get(0);


        for(Map.Entry<String,List<Double>> pair : currencyData.entrySet()) max = Collections.max(pair.getValue());

        presentDataForOneDay("poniedziałek",currencyData.get("poniedziałek"),max, sign);
        presentDataForOneDay("wtorek",currencyData.get("wtorek"),max, sign);
        presentDataForOneDay("środa",currencyData.get("środa"),max, sign);
        presentDataForOneDay("czwartek",currencyData.get("czwartek"),max, sign);
        presentDataForOneDay("piątek",currencyData.get("piątek"),max, sign);



        System.out.println("\n\n\n\n");

    }


    public void presentHelp(){
        System.out.println("\n Welcome in CurrencyInformator, here is the short description of how to use this program :)"+
                "\n\n date should be in shape yyyy-mm-dd , currency code is a three-letter code like : 'chf'" +
                "\n\n <-a> <currency-code> <date> : Gives information about gold price and defined currency ratio in given day " +
                "\n\n <-b> <date-start> <date-end> : gives information about average price of gold between given days " +
                "\n\n <-c> <start-date> :  Gives information about currency with highest amplitude in price between given date and actual date" +
                "\n\n <-d>  <date> : gives information about the currency with the lowest bid rate in defined day" +
                "\n\n <-e> <date> : gives a table of currencies in defined day, sorted by the amplitude between mid and ask rate.  "+
                "\n\n <-f> <currency-code> : gives information when the currency was cheapest and when the most expensive. " +
                "\n\n <-g> <currency-code> <startYear> <startMonth> <startWeek> <endYear> <endMonth> <endWeek> <sign> : shows a diagram of currency ratio changes in given period"
        );

        System.out.println("\n\n\n\n");

    }


    private void presentDataForOneDay(String day, List<Double> data, Double max, String sign){


        for(int i=0 ; i<data.size();i++){
            double numberOfSigns = (data.get(i)/max) * 100 - 40;

            System.out.print("\n "+ day+(i+1)+ " ");
            for(int j=0;j<numberOfSigns;j++) System.out.print(sign);
            System.out.print(" " + ((int) numberOfSigns));
        }
        System.out.println("\n");
    }

}
