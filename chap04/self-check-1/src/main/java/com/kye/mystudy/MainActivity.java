package com.kye.mystudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText  Name,age,address;
    Button bt;
    TextView result,result1,result2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.edt_Name);
        age = findViewById(R.id.edt_age);
        address = findViewById(R.id.edt_Juso);
        bt = findViewById(R.id.button);
        result = findViewById((R.id.textView));
        result1 = findViewById((R.id.textView2));
        result2 = findViewById((R.id.textView3));

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String Name1 = Name.getText().toString();
               String age1 = age.getText().toString();
               String address1 = address.getText().toString();


                result.setText("입력한 이름 : " + Name1);
                result1.setText("입력한 나이 : " + age1);
                result2.setText("입력한 주소 : " + address1);
                Toast.makeText(getApplicationContext(),"입력된 값 : " + Name1+","+age1+","+address1,Toast.LENGTH_LONG).show();

            }
        });
    }
    public void  onClose(View v){
        finish();
    }
}
