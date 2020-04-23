package com.cyberkyj.contactdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager {

    String Db_Name = "contatc.db";
    int version = 1;
    Context context;
    MyDBHelper myDBHelper;
    SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper(context);
    }

    public void dbOpen(){
        db = myDBHelper.getWritableDatabase();
    }
    public void insertData(String sql, ContactData data){
        String[] sqldata = data.getDataArray();
        db.execSQL(sql, sqldata);

    }

    public Cursor selectdata(String sql){
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public void deleteData(String sql, String[] args){
        db.execSQL(sql, args);
    }

    private class MyDBHelper extends SQLiteOpenHelper{

        public MyDBHelper(@Nullable Context context) {
            super(context, Db_Name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table contact(_id integer PRIMARY KEY AUTOINCREMENT, "+
                    "name text not null, "+
                    "phone text not null, "+
                    "email text not null);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
