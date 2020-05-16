package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kye.mycinema.R;

public class ContentsActivity extends AppCompatActivity {

    EditText editText,editText1;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        Button save_btn = findViewById(R.id.save_btn);
        Button finish_btn = findViewById(R.id.finish_btn);
        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                String contents = editText1.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"아이디를 입력하세요.",Toast.LENGTH_LONG).show();
                }else if(contents.isEmpty()){
                    Toast.makeText(getApplicationContext(),"내용을 입력하세요.",Toast.LENGTH_LONG).show();
                }else {
                    intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("contents", contents);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
