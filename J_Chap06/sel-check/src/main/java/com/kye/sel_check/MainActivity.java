package com.kye.sel_check;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText editText,edittext2;
    RadioButton radioButton1,radioButton2;

    Singer singer1,singer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        edittext2 = findViewById(R.id.editText2);
        radioGroup = findViewById(R.id.radioGroup2);
        radioButton1 = findViewById(R.id.radioButton7);
        radioButton2 = findViewById(R.id.radioButton8);
        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        singer1 = new Singer("가수1",0);
        singer2 = new Singer("가수2",0);
        ImageView imageView1 = findViewById(R.id.imageView);
        ImageView imageView2 = findViewById(R.id.imageView2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                int age = Integer.parseInt(edittext2.getText().toString());
                if(radioButton1.isChecked()){
                 singer1.setName(name);
                 singer1.setAge(age);
                 Toast.makeText(getApplicationContext(),"입력한 값이 첫번째 singer 객체에 설정되었습니다.",Toast.LENGTH_LONG).show();
                }else{
                    singer2.setName(name);
                    singer2.setAge(age);
                    Toast.makeText(getApplicationContext(),"입력한 값이 두번째 singer 객체에 설정되었습니다.",Toast.LENGTH_LONG).show();
                }

            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"가수의 이름 :"+singer1.getName()+"가수의 나이 :"+singer1.getAge(),Toast.LENGTH_LONG).show();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"가수의 이름 :"+singer2.getName()+"가수의 나이 :"+singer2.getAge(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
