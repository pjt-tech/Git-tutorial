package com.kye.chap06;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    DatePicker datePicker;
    TimePicker timePicker;
    TextView tvYear,tvMonth,tvDay,tvHour,tvMinute;
    RadioButton rdo_Date,rdo_Time;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chrono);
        rdo_Date = findViewById(R.id.radioDate);
        rdo_Time = findViewById(R.id.radioTime);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        tvYear = findViewById(R.id.txt_Year);
        tvMonth = findViewById(R.id.txt_Month);
        tvDay = findViewById(R.id.txt_Day);
        tvHour = findViewById(R.id.txt_Hour);
        tvMinute = findViewById(R.id.txtMinute);
        layout = findViewById(R.id.layout);
        rdo_Date.setVisibility(View.INVISIBLE);
        rdo_Time.setVisibility(View.INVISIBLE);
        datePicker.setVisibility(View.INVISIBLE);
        timePicker.setVisibility(View.INVISIBLE);

        chronometer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               chronometer.setBase(SystemClock.elapsedRealtime());
               chronometer.start();
               chronometer.setTextColor(Color.RED);
                rdo_Date.setVisibility(View.VISIBLE);
                rdo_Time.setVisibility(View.VISIBLE);

                return false;

            }
        });
        rdo_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.INVISIBLE);
            }
        });
        rdo_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.INVISIBLE);
                timePicker.setVisibility(View.VISIBLE);
            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                chronometer.stop();
                chronometer.setTextColor(Color.BLUE);

                tvYear.setText(Integer.toString(datePicker.getYear()));
                tvMonth.setText(Integer.toString(1+datePicker.getMonth()));
                tvDay.setText(Integer.toString(datePicker.getDayOfMonth()));
                tvHour.setText(Integer.toString(timePicker.getCurrentHour()));
                tvMinute.setText(Integer.toString(timePicker.getCurrentMinute()));
                rdo_Date.setVisibility(View.INVISIBLE);
                rdo_Time.setVisibility(View.INVISIBLE);
                datePicker.setVisibility(View.INVISIBLE);
                timePicker.setVisibility(View.INVISIBLE);

                return false;
            }
        });
    }
}
