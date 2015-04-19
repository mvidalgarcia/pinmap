package com.mvidalgarcia.pinmap;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mvidalgarcia.pinmap.model.Pin;
import com.mvidalgarcia.pinmap.tasks.ImageLoadTask;
import com.mvidalgarcia.pinmap.ws.PinWS;
import com.mvidalgarcia.pinmap.ws.impl.PinREST;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PinDetailsFragment extends Fragment {
    int idPin;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create ContextThemeWrapper from the original Activity Context with the custom theme
        Context context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);

        // Clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(context);

        // Inflate using the cloned inflater, not the passed in default
        View view =  localInflater.inflate(R.layout.pin_details_fragment, container, false);

        Bundle bundle = getArguments();
        idPin = bundle.getInt("id");
        printPinDetails(idPin, view);

        /* Callback to delete pin */
        view.findViewById(R.id.delete_pin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getView();
                if(view != null) {
                    PinWS service = new PinREST();
                    try {
                        service.deletePin(idPin);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {
                        // Go to map (Simulate tapping back button)
                        getActivity().onBackPressed();
                    }
                }

            }
        });

        return view;

    }

    /* Gets and prints Pin details */
    public void printPinDetails(int id, View view) {
        // Retrieve Pin from web service
        PinWS service = new PinREST();
        Pin pin = null;
        try {
            pin = service.getPinById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView date = (TextView) view.findViewById(R.id.date);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBarDetails);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgViewDetails);
        title.setText(pin.getTitle());
        description.setText(pin.getDescription());
        date.setText(epochToDateString(pin.getDate()));
        ratingBar.setRating(pin.getRating());
        // Asynchronous task to print photo on ImageView
        new ImageLoadTask(pin.getPhoto(), imageView).execute();
    }

    public String epochToDateString(long epoch) {
        Date date = new Date(epoch * 1000);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }

}