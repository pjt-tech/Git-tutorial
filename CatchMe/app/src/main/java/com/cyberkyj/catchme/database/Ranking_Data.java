package com.cyberkyj.catchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Ranking_Data extends SQLiteOpenHelper{
	
	private final String tag = "RankingHelper.java";
	private final static String db_name = "mole.db";
	private final String db_table_name = "rank";
	SQLiteDatabase db;
	static String result;
	
	
	public Ranking_Data(Context context) {
		super(context, db_name, null, 1);
		db = this.getWritableDatabase();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table if not exists "+ db_table_name + "("
				+ " rank integer PRIMARY KEY AUTOINCREMENT,"
				+ " player text, "
				+ " score integer)";
		db.execSQL(sql);
		db.rawQuery("SELECT * FROM "+db_table_name+" order by score desc",null);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + db_table_name);
		onCreate(db);
	}

	public void add(String a, int b){
		ContentValues val = new ContentValues();
//		val.put("score", id);
		val.put("player", a);
		val.put("score", b);
		db.insert(db_table_name, null, val);
		search_data();
	}
	public void delete(int delete){
		db.delete(db_table_name, "rank = "+ delete , null);
		search_data();
		
	}
	
	public void search_data(){
		String sql = "select * from "+ db_table_name;
		Cursor cur = db.rawQuery(sql, null);
		cur.moveToFirst();
	
		while(cur.isAfterLast()==false){

			String player = cur.getString(1);
			int score = cur.getInt(2);
			result = (player + "   " + score);
			Log.i(tag, result);
			cur.moveToNext();
		}
		cur.close();
	}
	
	public Cursor getAll(){
		return db.query(db_table_name, null, null,null,null,null,null);
	}
	public Cursor getAll_lineup(){
		return db.query(db_table_name, null, null,null,null,null,"score desc");
	}
	public Cursor getScore(int score){
		Cursor cur = db.query(db_table_name , null, "score = " + score , null,null,null,null);
		if(cur!=null&&cur.getCount() !=0)
			cur.moveToNext();
		return cur;
		
	}
	public int getCounter() {
		Cursor cur = null;
		String sql = "select * from " + db_table_name;
		cur = db.rawQuery(sql, null);
		int counter = 0;
		while (!cur.isAfterLast()) {
			cur.moveToNext();
			counter++;
		}
		return counter;
	}
}




