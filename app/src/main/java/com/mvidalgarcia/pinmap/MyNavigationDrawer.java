package com.mvidalgarcia.pinmap;

import android.os.Bundle;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MyNavigationDrawer extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
        this.addSection(newSection("My map", new MapFragment()));
        this.addSection(newSection("Let's pin", new FragmentIndex()));
    }
}