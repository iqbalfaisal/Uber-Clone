package Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sniper.uber.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Sniper on 12/26/2017.
 */

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    View myView;
    public CustomInfoWindow(Context context)
    {
        myView= LayoutInflater.from(context).inflate(R.layout.custon_rider_info_window,null);

    }
    @Override
    public View getInfoWindow(Marker marker) {

        TextView txtPickupTittle=((TextView)myView.findViewById(R.id.txtPickupInfo));
        txtPickupTittle.setText(marker.getTitle());
        TextView txtPickupSnippet=((TextView)myView.findViewById(R.id.txtPickupSnippet));
        txtPickupSnippet.setText(marker.getSnippet());

        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
