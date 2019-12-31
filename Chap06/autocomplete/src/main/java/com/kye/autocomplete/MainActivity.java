package com.kye.autocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = {"CSI-뉴욕","CSI-라스베가스","CSI-마이애미","Friend","Fringe","Lost"};

        AutoCompleteTextView auto = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,items);
        auto.setAdapter(adapter);

        MultiAutoCompleteTextView mult1 = findViewById(R.id.multiAutoCompleteTextView);
        MultiAutoCompleteTextView.CommaTokenizer token = new MultiAutoCompleteTextView.CommaTokenizer();
        mult1.setTokenizer(token);
        mult1.setAdapter(adapter);
    }
}
