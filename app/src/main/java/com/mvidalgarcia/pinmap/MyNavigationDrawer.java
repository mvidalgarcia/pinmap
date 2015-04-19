package com.mvidalgarcia.pinmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;


public class MyNavigationDrawer extends MaterialNavigationDrawer implements MaterialAccountListener {

    static final int SELECT_PHOTO = 100;

    @Override
    public void init(Bundle savedInstanceState) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                }
        }
    }

}