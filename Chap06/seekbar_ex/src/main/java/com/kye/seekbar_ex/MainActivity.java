package com.kye.seekbar_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    LinearLayout layout;
    TextView textView;
    int bright = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        layout = findViewById(R.id.layout);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate_left);
                seekBar.setProgress(bright);
                layout.setVisibility(View.VISIBLE);
                layout.startAnimation(animation);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(progress<10){
                        progress = 10;
                    }else if(progress>100){
                        progress=100;
                    }
                    bright=progress;
                    WindowManager.LayoutParams params =getWindow().getAttributes();
                    params.screenBrightness = (float)progress/100;
                    getWindow().setAttributes(params);
                    textView.setText("밝기 수준 : " + bright +"%");
                }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.translate_right);
                layout.setVisibility(View.GONE);
                layout.startAnimation(animation);

            }
        });

    }
}
