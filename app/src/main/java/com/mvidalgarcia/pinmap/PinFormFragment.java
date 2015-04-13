package com.mvidalgarcia.pinmap;


    import android.content.Context;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.view.ContextThemeWrapper;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;


public class PinFormFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create ContextThemeWrapper from the original Activity Context with the custom theme
        Context context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);

        // Clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(context);

        // Inflate using the cloned inflater, not the passed in default
        View view =  localInflater.inflate(R.layout.pin_form_fragment, container, false);

         /* Callback to create new pin */

        view.findViewById(R.id.new_pin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToPinForm();
                Toast.makeText(getActivity(), "Pin pressed",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}