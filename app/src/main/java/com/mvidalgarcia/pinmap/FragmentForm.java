package com.mvidalgarcia.pinmap;


    import android.content.Context;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.view.ContextThemeWrapper;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;


public class FragmentForm extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create ContextThemeWrapper from the original Activity Context with the custom theme
        Context context = new ContextThemeWrapper(getActivity(), R.style.AppTheme);

        // Clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(context);

        // Inflate using the cloned inflater, not the passed in default
        return localInflater.inflate(R.layout.form_fragment, container, false);

    }

}