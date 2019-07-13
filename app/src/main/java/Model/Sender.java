package Model;

import android.provider.ContactsContract;

/**
 * Created by Sniper on 12/26/2017.
 */

public class Sender {
    public String to;
    public Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Sender(String to, Notification notification) {

        this.to = to;
        this.notification = notification;
    }

    public Sender() {

    }
}
