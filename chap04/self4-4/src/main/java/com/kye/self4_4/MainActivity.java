package com.kye.self4_4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    RadioGroup radioGroup;
    RadioButton rdo_7, rdo_8, rdo_9;
    ImageView imageView;
    Button button,button2;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw = findViewById(R.id.switch1);
        textView = findViewById(R.id.textView2);
        radioGroup = findViewById(R.id.rdo_group);
        rdo_7 = findViewById(R.id.rdo_7);
        rdo_8 = findViewById(R.id.rdo_8);
        rdo_9 = findViewById(R.id.rdo_9);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                imageView.setImageResource(0);
                radioGroup.clearCheck();
                sw.setChecked(false);
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    textView.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    radioGroup.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    button2.setVisibility(View.INVISIBLE);
                }
            }

        });

    }
     public void onVersion(View v){
        switch (v.getId()){
            case R.id.rdo_7 :
                imageView.setImageResource(R.drawable.api50);
                break;
            case R.id.rdo_8 :
                imageView.setImageResource(R.drawable.api60);
                break;
            case R.id.rdo_9:
                imageView.setImageResource(R.drawable.api70);
                break;

        }
     }
}
