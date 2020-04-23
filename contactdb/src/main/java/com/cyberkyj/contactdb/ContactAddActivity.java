package com.cyberkyj.contactdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        Button btnAdd = findViewById(R.id.btnadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserDB();
                finish();
            }
        });
    }

    private void inserDB(){

        EditText edtName = findViewById(R.id.nameedit);
        EditText edtPhone = findViewById(R.id.phonenumberedit);
        EditText edtEmail = findViewById(R.id.emailedit);
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();

        ContactData data = new ContactData(name, phone, email);
        DBManager manager = new DBManager(this);
        manager.dbOpen();
        String sql ="insert into contact(name,phone,email) values(?,?,?);";
        manager.insertData(sql,data);

    }
}
