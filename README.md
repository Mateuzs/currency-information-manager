# currency-information-manager

Welcome to my app built with Java, which connects to the api.npb.pl server, gets and parses json data,
then shows it to the user.

## Getting Started

The simplest way to try this app is downloading the jar file and running it from Your command line.  

Basic option this app can take:  


`-a <currency-code> <date>` : Gives information about gold price and defined currency ratio in given day  
`-b <date-start> <date-end>` : gives information about average price of gold between given days  
`-c <start-date>` :  Gives information about currency with highest amplitude in price between given date and actual date  
`-d  <date>` : gives information about the currency with the lowest bid rate in defined day  
`-e <date>` : gives a table of currencies in defined day, sorted by the amplitude between mid and ask rate  
`-f <currency-code>` : gives information when the currency was cheapest and when the most expensive  
`-g <currency-code>` <startYear> <startMonth> <startWeek> <endYear> <endMonth> <endWeek> <sign> :  
shows a diagram of currency ratio changes in given period  

date should be in shape yyyy-mm-dd , currency code is a three-letter code, for example : chf  

Starting the app without any aption or input arugment, gives help info.  

Examples:  
```
java -jar CurrencyInformationManager.jar
java -jar CurrencyInformationManager.jar -a chf  
java -jar CurrencyInformationManager.jar -g eur  
java -jar CurrencyInformationManager.jar -g gbp 2012 01 1 2018 02 3 \*  
```
