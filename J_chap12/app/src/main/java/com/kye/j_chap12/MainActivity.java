package com.kye.j_chap12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] names = {"철수", "영희", "수지", "민희", "지민"};
    int count = 0;
    ArrayList<person> persons = new ArrayList<person>();
    TextView txt_count;
    LinearLayout layout;
    EditText edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_create = findViewById(R.id.btn_create);
        Button btn_add = findViewById(R.id.btn_add);
        txt_count = findViewById(R.id.txt_Count);
        edit_name = findViewById(R.id.edit_name);
        layout = findViewById(R.id.layout);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nameIndex = count % 5;
                person curPerson = new person(names[nameIndex]);
                persons.add(curPerson);
                Toast.makeText(getApplicationContext(), names[nameIndex] + "이(가)만들어졌습니다.", Toast.LENGTH_LONG).show();
                String curName = curPerson.getName();
                TextView textView = new TextView(getApplicationContext());
                textView.setText(curName);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                layout.addView(textView, params);
                count++;
                txt_count.setText(count + " 명");

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        String curName = edit_name.getText().toString();
        person curPerson = new person(curName);
        persons.add(curPerson);
        Toast.makeText(getApplicationContext(), curName + "이(가)만들어졌습니다.", Toast.LENGTH_LONG).show();
        String name = curPerson.getName();
        TextView textView = new TextView(getApplicationContext());
        textView.setText(name);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layout.addView(textView, params);
        txt_count.setText(persons.size() + " 명");
        }
        });
        }
}
