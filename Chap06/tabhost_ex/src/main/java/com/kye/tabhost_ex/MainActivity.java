package com.kye.tabhost_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("DOG").setIndicator(null, getResources().getDrawable(R.drawable.icon_dog));
        tabSpec1.setContent(R.id.tab1);
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("CAT").setIndicator(null, getResources().getDrawable(R.drawable.icon_cat));
        tabSpec2.setContent(R.id.tab2);
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("RABBIT").setIndicator(null, getResources().getDrawable(R.drawable.icon_rabbit));
        tabSpec3.setContent(R.id.tab3);
        tabHost.addTab(tabSpec3);

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("HORSE").setIndicator(null, getResources().getDrawable(R.drawable.icon_horse));
        tabSpec4.setContent(R.id.tab4);
        tabHost.addTab(tabSpec4);

        tabHost.setCurrentTab(0);
    }
}
