package Model;

/**
 * Created by Sniper on 12/29/2017.
 */

public class Rate {
    private String rates;

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Rate(String rates, String comments) {

        this.rates = rates;
        this.comments = comments;
    }

    public Rate() {

    }

    private String comments;
}
