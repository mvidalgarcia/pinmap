package com.mvidalgarcia.pinmap;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mvidalgarcia.pinmap.model.Pin;
import com.mvidalgarcia.pinmap.ws.PinWS;
import com.mvidalgarcia.pinmap.ws.impl.PinREST;

import java.util.AbstractList;
import java.util.ArrayList;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_location_info, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 17.385044;
        double longitude = 78.486671;

        // Create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("My trip to New Delhi").snippet("View more details");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // Adding marker
        googleMap.addMarker(marker);
        /*
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(17.385044, 78.486671)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        */

        // Place camera in last marker position (no zoom)
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(17.385044, 78.486671)).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Disable toolbar (shown when marker is tapped)
        googleMap.getUiSettings().setMapToolbarEnabled(false);


        // "Details" info window click listener
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                goToPinDetails("My trip to New Delhi");
            }
        });


        // Callback to pin form
        v.findViewById(R.id.pin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPinForm();
            }
        });

        return v;
    }

    public void goToPinForm() {
        Fragment fragment = new PinFormFragment();
        //Bundle data = new Bundle();
        //data.putString("Test","Banana");
        //fragment.setArguments(data);
        ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(fragment,"Pin new place!");
    }

    public void goToPinDetails(String pinTitle) {
        Fragment fragment = new PinDetailsFragment();
        //Bundle data = new Bundle();
        //data.putString("Test","Banana");
        //fragment.setArguments(data);
        ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(fragment, pinTitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Log.i("MyNavidationDrawer", "PROBANDO METODO JAVA");
            PinWS service = new PinREST();
            //ArrayList<Pin> pins = (ArrayList<Pin>)service.getPinsByGooglePlusId("904972304704039106999");
            //Pin pin = service.getPinById(1);
            Pin pin = new Pin("JJJJune 2056 in Nigeria", "Nigeria rules!",
                    3.0, 52.431898, 14.740681, "photo_nigeria", 1508116466, "904972304704039106999");
            service.insertPin(pin);
            //service.deletePin(1);
            ArrayList<Pin> pins = (ArrayList<Pin>)service.getPinsByGooglePlusId("904972304704039106999");
            if (pin != null)
                Log.i("RETURN", String.valueOf(pins.size()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}