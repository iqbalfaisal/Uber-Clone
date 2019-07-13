package Remote;

import Model.FCMResponse;
import Model.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Sniper on 12/26/2017.
 */

public interface IFCMService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAArzigeMo:APA91bG9eFnr3aEmROddIq4BQfNbAcwmnA28bfdUo8-IPqg2tLBA0LgDZfFHwCyk5uRrW3h_xF78j2xA6WHVQyDLljKgAPhR9Uwi9bjfo44Su7BKiQDoR9Z-7vOlGHR9IZ8E2eO2J6vl"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);}
