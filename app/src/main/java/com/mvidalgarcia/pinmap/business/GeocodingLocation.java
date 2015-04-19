package com.mvidalgarcia.pinmap.business;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GeocodingLocation {
    private static final String TAG = "GeocodingLocation";

    public static double getLatitudeFromLocation(final String locationAddress, final Context context) {
        return getFromLocation(locationAddress, context, "lat");
    }

    public static double getLongitudeFromLocation(final String locationAddress, final Context context) {
        return getFromLocation(locationAddress, context, "lng");
    }

    public static double getFromLocation(final String locationAddress, final Context context, String type) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                if (type.equals("lat"))
                    return address.getLatitude();
                else if (type.equals("lng"))
                    return address.getLongitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return -1;
    }

}
