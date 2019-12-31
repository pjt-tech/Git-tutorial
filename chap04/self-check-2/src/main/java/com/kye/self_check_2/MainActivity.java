package com.kye.self_check_2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText3,editText4,editText5;
    Button bt;
    FrameLayout fl;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     editText3 = findViewById(R.id.editText2);
     editText4 = findViewById(R.id.editText3);
     editText5 = findViewById(R.id.editText4);
     bt = findViewById(R.id.button);
     fl = findViewById(R.id.frameLayout2);

     fl.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                double x = event.getX();
                double y = event.getY();
                num++;
                editText3.setText(String.valueOf(x));
                editText4.setText(String.valueOf(y));
                String touch = "터치한 횟수 : " + num;
                editText5.setText(touch);
            }

             return false;
         }
     });
     bt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             finish();
         }
     });
    }
}
