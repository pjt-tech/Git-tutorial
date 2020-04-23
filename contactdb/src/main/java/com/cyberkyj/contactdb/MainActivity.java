package com.cyberkyj.contactdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnadd);
        Button btnDel = findViewById(R.id.btndelete);
        Button btnSelect = findViewById(R.id.btnselect);
        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSelect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnadd:
                intent = new Intent(getApplicationContext(), ContactAddActivity.class);
                startActivity(intent);
                break;
            case R.id.btndelete:
                intent = new Intent(getApplicationContext(), ContactDelActivity.class);
                startActivity(intent);
                break;
            case R.id.btnselect:
                intent = new Intent(getApplicationContext(), ContactSelectActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
