package main;

/**
 * Creates a Simple Node with information about the currency and its rates.
 */
public class JsonNode {

    private String currency;
    private Double ask;
    private Double bid;
    private Double amplitude;



    public JsonNode (String currency, Double ask, Double bid){

        this.currency = currency;
        this.ask = ask;
        this.bid = bid;
        this.amplitude = ask - bid;

    }



    public Double getAmplitude(){ return amplitude;}


    public String getCurrency(){return currency;}


    public Double getAsk(){return ask;}


    public Double getBid(){return bid;}


}
