package main;


import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Main {

 public static void main (String [] args) throws IOException, ParseException {


     IRouterHTTP router = new RouterHTTP();
     IJsonParser parser = new JsonParser();
     IJsonDataPresenter presenter = new JsonDataPresenter();
     IJsonDataManager manager =  new JsonDataManager();
     String currency, strDate1, strDate2;

     DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
     DateTime startDateTime, endDateTime;




     try {

         if(args.length == 0) presenter.presentHelp();

         for (int i = 0; i < args.length; i++) {
             switch (args[i]) {
                 case "-a":

                     currency = args[++i];
                     strDate1 = args[++i];


                      startDateTime = new DateTime(strDate1);

                     // Checks if the start date is correct according to data-acces on NBP page.
                     if(startDateTime.isBefore(new DateTime(2013, 01,02, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(startDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                         presenter.presentIncorrectStartDateError();
                         return;
                     }


                     presenter.presentExchangeRate(
                             parser.parseJsonDataToObject(
                                     router.sendRequest("http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + strDate1 + "/?format=json")));

                     presenter.presentGoldPrice(
                             parser.parseJsonDataToArray(
                                     router.sendRequest("http://api.nbp.pl/api/cenyzlota/"+strDate1+"/?format=json")));

                     break;


                 case "-b":


                         strDate1 = args[++i];
                         strDate2 = args[++i];


                      startDateTime = new DateTime(strDate1);
                      endDateTime = new DateTime(strDate2);

                      // Checks if the start date is correct according to data-acces on NBP page.
                     if(startDateTime.isBefore(new DateTime(2013, 01,02, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(startDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(endDateTime.isBefore(new DateTime(2002, 01,01, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(endDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                         presenter.presentIncorrectStartDateError();
                         return;
                     }

                     presenter.presentAverageGoldPrice(manager.computeAverageGoldPrice(strDate1, strDate2));
                     break;


                 case "-c":

                     strDate1 = args[++i];
                     startDateTime = new DateTime(strDate1);

                     // Checks if the start date is correct according to data-acces on NBP page.
                     if(startDateTime.isBefore(new DateTime(2002, 01,01, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(startDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                        presenter.presentIncorrectStartDateError();
                        return;
                     }

                     presenter.presentCurrencyWithHighestAmplitude(manager.findCurrencyWithHighestAmplitude(strDate1));
                     break;


                 case "-d":

                     strDate1 = args[++i];

                     startDateTime = new DateTime(strDate1);

                     // Checks if the start date is correct according to data-acces on NBP page.
                     if(startDateTime.isBefore(new DateTime(2002, 01,01, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(startDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                         presenter.presentIncorrectStartDateError();
                         return;
                     }


                     presenter.presentCheapestCurency(
                            manager.findCheapestCurrency(
                                    parser.parseJsonDataToArray(
                                            router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/c/"+strDate1+"/?format=json"))));

                    break;


                 case "-e":

                     strDate1 = args[++i];
                     startDateTime = new DateTime(strDate1);

                     // Checks if the start date is correct according to data-acces on NBP page.
                     if(startDateTime.isBefore(new DateTime(2002, 01,01, 0,0,0))) {
                         presenter.presentIncorrectStartDateError();
                         return;
                     }
                     if(startDateTime.isAfter(new DateTime(new LocalDateTime().toDateTime()))){
                         presenter.presentIncorrectStartDateError();
                         return;
                     }

                     presenter.presentSortedTableOfCurrencies(
                             manager.sortCurrencies(
                                     parser.parseJsonDataToArray(
                                             router.sendRequest("http://api.nbp.pl/api/exchangerates/tables/c/"+strDate1+"/?format=json"))));

                     break;

                 case "-f":

                     currency = args[++i];

                     presenter.presentHighestAndLowestPriceOfCurrency( manager.findEdgePricesForCurrency(currency));
                     break;

                 case "-g":




                     currency = args[++i];
                     String startYear = args[++i], startMonth = args[++i], startWeek = args[++i], endYear = args[++i], endMonth = args[++i], endWeek = args[++i], sign = args[++i];

                     if(Integer.parseInt(startYear) < 2002 || Integer.parseInt(endYear) < 2002) presenter.presentIncorrectStartDateError();
                     if(Integer.parseInt(startYear) > new LocalDateTime().getYear() || Integer.parseInt(endYear) > new LocalDateTime().getYear()) presenter.presentIncorrectStartDateError();

                     presenter.presentDiagram(manager.createDiagram(currency, startYear, startMonth, startWeek, endYear, endMonth, endWeek), sign);

                    break;
                 default:

                     presenter.presentHelp();
                     return;
             }
         }




     }
     catch (IllegalArgumentException e){System.out.println("\n"+e + "\n\nYou probably have a misspelling in Your arguments.");}
     catch (IOException e) {System.out.println("\n"+e+" \n\nError 400 - invalid date range."+ "\nProbably there is no data for specified day. ");}
     catch (IndexOutOfBoundsException e) {System.out.println("\nSyntax error. Make sure, You defined proper arguments.");}
 }

}
