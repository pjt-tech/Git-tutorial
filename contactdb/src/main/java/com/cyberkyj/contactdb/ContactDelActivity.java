package com.cyberkyj.contactdb;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ContactDelActivity extends AppCompatActivity {

    ListView listView;
    Cursor cursor;
    ArrayList<ContactData> items = new ArrayList<>();
    SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_del);

        listView = findViewById(R.id.listView);
        final DBManager manager = new DBManager(this);
        manager.dbOpen();
        cursor = manager.selectdata("select * from contact order by _id desc;");
        if(cursor.moveToNext()){
            ContactData data = new ContactData(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            items.add(data);

        }

        String[] columns = {"name", "phone", "email"};
        int[] to = {R.id.nametxt, R.id.phonenumbertxt, R.id.emailtxt};
        adapter = new SimpleCursorAdapter(this,R.layout.list_item,cursor,columns,to);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ContactData data = items.get(position);
                String[] args = {data.getPhone()};
                manager.deleteData("delete from contact where phone=?",args);
                adapter.notifyDataSetChanged();
                finish();
                return true;
            }
        });

    }
}
