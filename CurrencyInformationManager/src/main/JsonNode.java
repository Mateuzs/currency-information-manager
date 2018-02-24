package main;

/**
 * Creates a Simple Node with information about the currency and its rates.
 */
public class JsonNode {

    private String currency;
    private Double ask;
    private Double bid;
    private Double amplitude;

    /**
     *Creates a node.
     * @param currency
     * @param ask
     * @param bid
     */

    public JsonNode (String currency, Double ask, Double bid){

        this.currency = currency;
        this.ask = ask;
        this.bid = bid;
        this.amplitude = ask - bid;

    }

    /**
     * Returns amplitude.
     * @return Double
     */
    public Double getAmplitude(){ return amplitude;}

    /**
     * Returns currency name.
     * @return Double
     */
    public String getCurrency(){return currency;}

    /**
     * Returns ask rate.
     * @return Double
     */
    public Double getAsk(){return ask;}

    /**
     * return bid rate.
     * @return Double
     */
    public Double getBid(){return bid;}


}
