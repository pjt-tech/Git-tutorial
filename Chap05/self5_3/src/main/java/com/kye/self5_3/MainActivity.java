package com.kye.self5_3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout Linear = new LinearLayout(getApplicationContext());
        Linear.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams prams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams naemlayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(Linear,prams);

        final EditText editText = new EditText(getApplicationContext());
        editText.setHint("이름을 입력하세요.");


        Button btn1 = new Button(getApplicationContext());
        btn1.setText("버튼입니다.");
        btn1.setBackgroundColor(Color.rgb(0,255,0));



       final TextView textView = new TextView(getApplicationContext());
        textView.setText("버튼입력값 : ");
        textView.setTextSize(20);



        layout.addView(editText,naemlayout);
        layout.addView(btn1,naemlayout);
        layout.addView(textView,naemlayout);
        Linear.addView(layout,prams);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("버튼입력값 : " + editText.getText().toString());
            }
        });


    }
}
