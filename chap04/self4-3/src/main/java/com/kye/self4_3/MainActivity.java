package com.kye.self4_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textResult = findViewById(R.id.textResult);

        Button btnadd = findViewById(R.id.btnAdd);
        Button btnsub = findViewById(R.id.btnSub);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnRem = findViewById(R.id.btnRem);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                if(num1.equals("") || num2.equals("")){
                    Toast.makeText(getApplicationContext(),"입력 값이 비었습니다.",Toast.LENGTH_LONG).show();
                }else{
                    Double result = Double.parseDouble(num1)+Double.parseDouble(num2);
                    result = (int)(result*10)/10.0; //소수점아래 1자리까지만 출력
                    textResult.setText("계산결과 : " + result.toString());
                }

            }
        });
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                if(num1.equals("") || num2.equals("")){
                    Toast.makeText(getApplicationContext(),"입력 값이 비었습니다.",Toast.LENGTH_LONG).show();
                }else{
                    Double result = Double.parseDouble(num1)-Double.parseDouble(num2);
                    result = (int)(result*10)/10.0; //소수점아래 1자리까지만 출력
                    textResult.setText("계산결과 : " + result.toString());
                }

            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                if(num1.equals("") || num2.equals("")){
                    Toast.makeText(getApplicationContext(),"입력 값이 비었습니다.",Toast.LENGTH_LONG).show();
                }else{
                    Double result = Double.parseDouble(num1)*Double.parseDouble(num2);
                    result = (int)(result*10)/10.0; //소수점아래 1자리까지만 출력
                    textResult.setText("계산결과 : " + result.toString());
                }

            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                if(num1.equals("") || num2.equals("")){
                    Toast.makeText(getApplicationContext(),"입력 값이 비었습니다.",Toast.LENGTH_LONG).show();
                }else{
                    if(num2.equals("0")){
                        Toast.makeText(getApplicationContext(),"0으로 나누면 안됩니다.",Toast.LENGTH_LONG).show();
                    }else{

                    }
                    Double result = Double.parseDouble(num1)/Double.parseDouble(num2);
                    result = (int)(result*10)/10.0; //소수점아래 1자리까지만 출력
                    textResult.setText("계산결과 : " + result.toString());
                }

            }
        });
        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                if(num1.equals("") || num2.equals("")){
                    Toast.makeText(getApplicationContext(),"입력 값이 비었습니다.",Toast.LENGTH_LONG).show();
                }
                else if(num2.equals("0")){
                    Toast.makeText(getApplicationContext(),"0으로 나누면 안됩니다.",Toast.LENGTH_LONG).show();
                    }

                else
                    {

                    }
                    Double result = Double.parseDouble(num1)%Double.parseDouble(num2);
                    textResult.setText("계산결과 : " + result.toString());


            }
        });


    }
}
