package com.cyberkyj.contactdb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactSelectActivity extends AppCompatActivity {

    ListView listView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_select);

        listView = findViewById(R.id.listView);
        Button btnClose = findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DBManager manager = new DBManager(this);
        manager.dbOpen();
        cursor = manager.selectdata("select * from contact order by _id desc;");
        String[] columns = {"name", "phone", "email"};
        int[] to = {R.id.nametxt, R.id.phonenumbertxt, R.id.emailtxt};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_item,cursor,columns,to);
        listView.setAdapter(adapter);

    }
}
