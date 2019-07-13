package Model;

/**
 * Created by Sniper on 12/26/2017.
 */

public class Result {
    public String message_id;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public Result(String message_id) {

        this.message_id = message_id;
    }

    public Result() {

    }
}
