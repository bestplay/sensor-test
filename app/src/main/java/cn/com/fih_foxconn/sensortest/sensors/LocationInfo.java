package cn.com.fih_foxconn.sensortest.sensors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import cn.com.fih_foxconn.sensortest.LogUtil;

/**
 * Created by waynelu on 21/1/16.
 */
public class LocationInfo {
    private Context mContext;

    private String Log_tag = LocationInfo.class.getSimpleName();

    LocationManager locationManager;

    public LocationInfo (Context context){
        mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        String provider;

        // get all available provider
        List<String> providerList = locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else {
            Toast.makeText(mContext,"No location provider to use",Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null){
            showLocation(location);
        }

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                showLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);


    }

    public void showLocation(Location location){
        String currentPosition = "latitude is " + location.getLatitude() + "\n" + "longitude is " + location.getLongitude();
        LogUtil.d(Log_tag,currentPosition);
    }

}
