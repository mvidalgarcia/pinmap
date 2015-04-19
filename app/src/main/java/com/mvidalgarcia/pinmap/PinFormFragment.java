package com.mvidalgarcia.pinmap;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mvidalgarcia.pinmap.business.GeocodingLocation;
import com.mvidalgarcia.pinmap.model.Pin;
import com.mvidalgarcia.pinmap.ws.PinWS;
import com.mvidalgarcia.pinmap.ws.impl.PinREST;

import java.io.IOException;


public class PinFormFragment extends Fragment {
    private static final String TAG = "PinFormFragment";
    public final static String EPOCH = "com.mvidalgarcia.pinmap.EPOCH";
    private static final String GPLUSID = "904972304704039106999";
    private static final String PHOTOS_URI = "http://156.35.95.75:8080/";
    private long epoch = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create ContextThemeWrapper from the original Activity Context with the custom theme
        Context context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);

        // Clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(context);

        // Inflate using the cloned inflater, not the passed in default
        View view =  localInflater.inflate(R.layout.pin_form_fragment, container, false);

         /* Callback to pick photo */

        view.findViewById(R.id.pick_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                epoch = System.currentTimeMillis()/1000;
                intent.putExtra(EPOCH, String.valueOf(epoch));
                startActivity(intent);
            }
        });

        /* Callbacks to manage radio buttons */

        view.findViewById(R.id.radioButtonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText place = (EditText) getView().findViewById(R.id.editTextPlace);
                place.setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.radioButtonGPS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText place = (EditText) getView().findViewById(R.id.editTextPlace);
                place.setVisibility(View.GONE);
            }
        });


         /* Callback to create new pin */

        view.findViewById(R.id.new_pin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getView();
                if(view != null) {
                    String photo_url = "";
                    EditText title = (EditText) view.findViewById(R.id.formTitle);
                    EditText description = (EditText) view.findViewById(R.id.formDescription);
                    RatingBar rating = (RatingBar) view.findViewById(R.id.ratingBar);
                    // If epoch not established (no photo)
                    if (epoch == -1) {
                        epoch = System.currentTimeMillis() / 1000;
                    }
                    else {
                        photo_url = PHOTOS_URI + String.valueOf(epoch) + ".jpg";
                    }
                    EditText place = (EditText) view.findViewById(R.id.editTextPlace);
                    double lat = GeocodingLocation.getLatitudeFromLocation(place.getText().toString(), getActivity());
                    double lng = GeocodingLocation.getLongitudeFromLocation(place.getText().toString(), getActivity());
                    PinWS service = new PinREST();
                    Pin pin = new Pin(title.getText().toString(), description.getText().toString(),
                            rating.getRating(), lat, lng, photo_url, (int)epoch, GPLUSID);
                    try {
                        service.insertPin(pin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Go to map (Simulates tapping back button)
                    getActivity().onBackPressed();
                }
            }
        });


        return view;
    }



}