package com.kye.gridlayout_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText1,editText2;
    TextView textResult;
    Button[] numButton = new Button[10];
    int[] numBtnIds = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
                        R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        textResult= findViewById(R.id.txtResult);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnDiv = findViewById(R.id.btnDiv);

        for(int i = 0; i<numBtnIds.length; i++){
            numButton[i] = findViewById(numBtnIds[i]);
        }
        for(int i = 0; i<numBtnIds.length; i++){
            final int index;
            index = i;
            numButton[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editText1.isFocused()==true){
                        String num1 = editText1.getText().toString()+numButton[index].getText().toString();
                        editText1.setText(num1);
                    }else if(editText2.isFocused()==true){
                        String num2 = editText2.getText().toString()+numButton[index].getText().toString();
                        editText2.setText(num2);
                    }else{
                        Toast.makeText(getApplicationContext(),"editText를 먼저 선택해주세요.",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                int result = Integer.parseInt(num1) + Integer.parseInt(num2);
                textResult.setText("계산결과 : " +result);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                int result = Integer.parseInt(num1) - Integer.parseInt(num2);
                textResult.setText("계산결과 : " +result);
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                int result = Integer.parseInt(num1) * Integer.parseInt(num2);
                textResult.setText("계산결과 : " +result);
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = editText1.getText().toString();
                String num2 = editText2.getText().toString();
                int result = Integer.parseInt(num1) / Integer.parseInt(num2);
                textResult.setText("계산결과 : " +result);
            }
        });

    }
}
