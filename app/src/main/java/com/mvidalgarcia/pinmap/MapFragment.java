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
import java.util.HashMap;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    HashMap<Marker, Integer> markerHashMap = new HashMap<>();

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

        return v;
    }

    public void goToPinForm() {
        Fragment fragment = new PinFormFragment();
        ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(fragment,"Pin new place!");
    }

    public void goToPinDetails(Marker marker) {
        int id = markerHashMap.get(marker);
        Fragment fragment = new PinDetailsFragment();
        Bundle data = new Bundle();
        data.putInt("id", id);
        fragment.setArguments(data);
        ((MaterialNavigationDrawer)this.getActivity()).setFragmentChild(fragment, marker.getTitle());
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
            Log.i("MyNavidationDrawer", "Getting pins from web service...");
            PinWS service = new PinREST();
            ArrayList<Pin> pins = (ArrayList<Pin>)service.getPinsByGooglePlusId("904972304704039106999");
            if (pins.size() != 0){
                mMapView = (MapView) view.findViewById(R.id.mapView);
                googleMap = mMapView.getMap();
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                for (final Pin pin: pins) {
                    // Create marker
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(pin.getLat(), pin.getLng()))
                            .title(pin.getTitle())
                            .snippet("View more details");

                    // Changing marker icon
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

                    // Adding marker
                    Marker finalMarker = googleMap.addMarker(marker);

                    // Save marker with its Pin id
                    markerHashMap.put(finalMarker, pin.getId());

                    // "Details" info window click listener
                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            goToPinDetails(marker);
                        }
                    });

                    // Callback to pin form
                    view.findViewById(R.id.pin_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToPinForm();
                        }
                    });

                    // Place camera in last marker position (no zoom)
                    if (pins.lastIndexOf(pin) == pins.size()-1){
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(pin.getLat(), pin.getLng())).build();
                        googleMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }

                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}