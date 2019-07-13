package Common;

import android.location.Location;

import Model.Rider;
import Model.User;
import Remote.FCMClient;
import Remote.IFCMService;
import Remote.IGoogleAPI;
import Remote.RetrofitClient;

/**
 * Created by Sniper on 12/25/2017.
 */

public class Common {

    public static boolean isDriverFound = false;
    public static String driverId = "";

    public static final String driver_tbl="Drivers";
    public static final String user_driver_tbl="DriversInformation";
    public static final String user_rider_tbl="RidersInformation";
    public static final String pickup_request_tbl="PickupRequest";
    public static final String token_tbl="Tokens";
    public static final String rate_detail_tbl="RateDetails";

    public static User currentUser;
    public static Rider currentRider;

    public  static Location mLastLocation=null;

    public static final int PICK_IMAGE_REQUEST=9999;



    public static final String baseURL="https://maps.googleapis.com";
    public static final String fcmURL="https://fcm.googleapis.com/";
    public static final String user_field="usr";
    public static final String pwd_field="pwd";
    public static final String rider_field="rider usr";
    public static final String rider_pwd_field="rider pwd";


    public static double base_fare=100;
    public static double time_rate=3.15;
    public static double distance_rate=6.84;

    public static double formulaPrice(double km,double min)
    {
        return base_fare+(distance_rate*km)+(time_rate*min);
    }



    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}
