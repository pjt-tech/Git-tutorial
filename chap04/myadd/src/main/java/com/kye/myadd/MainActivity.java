package com.kye.myadd;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //전역변수
    EditText num1,num2;
    TextView result;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //지역변수
        num1 = (EditText)findViewById(R.id.edt_Num1);
        num2 = (EditText)findViewById(R.id.edt_Num2);
        result = (TextView)findViewById(R.id.txt_Result);
        add = (Button)findViewById(R.id.btn_Add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = Integer.parseInt(num1.getText().toString());
                int number2 = Integer.parseInt(num2.getText().toString());
                int sum = number1 + number2;
                result.setText(String.valueOf(sum));
            }
        });
    }
}
