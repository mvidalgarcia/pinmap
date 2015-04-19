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


public class PinFormFragment extends Fragment {

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
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, MyNavigationDrawer.SELECT_PHOTO);
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
                    EditText title = (EditText) view.findViewById(R.id.formTitle);
                    EditText description = (EditText) view.findViewById(R.id.formDescription);
                    RatingBar rating = (RatingBar) view.findViewById(R.id.ratingBar);
                    long epoch = System.currentTimeMillis()/1000;
                    EditText place = (EditText) view.findViewById(R.id.editTextPlace);
                    double lat = GeocodingLocation .getLatitudeFromLocation(place.getText().toString(), getActivity());
                    double lng = GeocodingLocation .getLongitudeFromLocation(place.getText().toString(), getActivity());
                    String toastTxt = title.getText() + " " + description.getText() +
                            " " + String.valueOf(rating.getRating()) + " " + String.valueOf(epoch);
                    if (lat != -1 && lng != -1)
                        toastTxt += " " + lat + " " + lng;
                    Toast.makeText(getActivity(), toastTxt, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

}