package com.kye.studend_school;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText,editText2;
    TextView textView,textView2;
    School school = new School("학교");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = editText.getText().toString();
                String curAge = editText2.getText().toString();
                int ages = Integer.parseInt(curAge);
                Student student = new Student(curName,ages);
                school.addItem(student);
                Toast.makeText(getApplicationContext(), "학생 객체 리스트에 추간된 총수는 : "+ school.size(), Toast.LENGTH_LONG).show();
                TextView textView = new TextView(getApplicationContext());
                textView.setText("추가된 학생의 총 수 : "+ school.size());
            }
        });
    }
    public void onList(View v){
        String output = school.toString();
        textView2.setText(output);

    }
}
