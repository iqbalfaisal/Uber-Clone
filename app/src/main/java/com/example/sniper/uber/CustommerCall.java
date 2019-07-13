package com.example.sniper.uber;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Common.Common;
import Model.FCMResponse;
import Model.Notification;
import Model.Sender;
import Model.Token;
import Remote.IFCMService;
import Remote.IGoogleAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustommerCall extends AppCompatActivity {

    TextView txtTime,txtAddress,txtDistance;
    MediaPlayer mediaPlayer;
    IGoogleAPI mService;
    Button btnAccept,btnCancel;

    String customerId;

    IFCMService mFCMService;
    double lng,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custommer_call);
        mService=Common.getGoogleAPI();

        mFCMService=Common.getFCMService();

        txtAddress=(TextView)findViewById(R.id.txtAddress);
        txtTime=(TextView)findViewById(R.id.txtTime);
        txtDistance=(TextView)findViewById(R.id.txtDistance);

        btnAccept=(Button)findViewById(R.id.btnAccept);
        btnCancel=(Button)findViewById(R.id.btnDecline);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(customerId))
                    cancelBooking(customerId);

            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustommerCall.this,DriverTracking.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("customerId",customerId);
                startActivity(intent);
                finish();
            }
        });

        mService=Common.getGoogleAPI();

        mediaPlayer=MediaPlayer.create(this,R.raw.ringtone);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if(getIntent()!=null)
        {
            lat=getIntent().getDoubleExtra("lat",-1.0);
             lng=getIntent().getDoubleExtra("lng",-1.0);
            customerId=getIntent().getStringExtra("customerId");


            getDirection(lat,lng);
        }
    }

    private void cancelBooking(String customerId) {
        Token token=new Token(customerId);

        Notification notification=new Notification("Cancel","Driver has cancelled your request ");
        Sender sender=new Sender(token.getToken(),notification);

        mFCMService.sendMessage(sender)
                .enqueue(new Callback<FCMResponse>() {
                    @Override
                    public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                        if (response.body().success==1)
                        {
                            Toast.makeText(CustommerCall.this,"Cancelled",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<FCMResponse> call, Throwable t) {

                    }
                });
    }

    private void getDirection(double lat,double lng) {

        String requestApi=null;
        try {
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + Common.mLastLocation.getLatitude() + "," + Common.mLastLocation.getLongitude()+ "&" +
                    "destination=" +lat+","+lng+ "&"
                    + "key=" + getResources().getString(R.string.google_direction_key);
            Log.d("your url", requestApi);
            mService.getPath(requestApi).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray routes=jsonObject.getJSONArray("routes");
                        JSONObject object=routes.getJSONObject(0);
                        JSONArray legs=object.getJSONArray("legs");
                        JSONObject legsObject=legs.getJSONObject(0);
                        JSONObject distance=legsObject.getJSONObject("distance");
                        txtDistance.setText(distance.getString("text"));
                        JSONObject time=legsObject.getJSONObject("duration");
                        txtTime.setText(time.getString("text"));

                        String address=legsObject.getString("end_address");
                        txtAddress.setText(address);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(CustommerCall.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();

    }

    @Override
    protected void onPause() {
        mediaPlayer.release();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mediaPlayer.release();
        super.onStop();
    }
}
