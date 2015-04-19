package com.mvidalgarcia.pinmap;

import android.content.ContentResolver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;


public class MyNavigationDrawer extends MaterialNavigationDrawer implements MaterialAccountListener {

    private static final String TAG = "MyNavigationDrawer";
    static final int SELECT_PHOTO = 100;
    static ContentResolver contentResolver = null;

    @Override
    public void init(Bundle savedInstanceState) {
        contentResolver = getContentResolver();

        // add accounts
        MaterialAccount account = new MaterialAccount(this.getResources(),"Marco Vidal García","marcovidal67@gmail.com",R.drawable.gplus_profile, R.drawable.gplus_bg);
        this.addAccount(account);

        this.setAccountListener(this);

        this.addSection(newSection("My map", new MapFragment()).setSectionColor(Color.parseColor("#0097A7")));
        this.addSection(newSection("My stats", new StatisticsFragment()).setSectionColor(Color.parseColor("#0097A7")));

        // Go to map when 'back' is tapped
        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);
    }

    @Override
    public void onAccountOpening(MaterialAccount account) {
        Log.i("MyNavigationDrawer", "FAB pressed");
        Toast.makeText(this, "onAccountOpening", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        Toast.makeText(this, "onChangeAccount", Toast.LENGTH_SHORT).show();
    }

}