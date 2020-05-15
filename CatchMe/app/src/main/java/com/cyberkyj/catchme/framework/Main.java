package com.cyberkyj.catchme.framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cyberkyj.catchme.R;
import com.cyberkyj.catchme.database.Ranking_Data;



public class Main extends Activity implements View.OnClickListener {

		private final String tag = "main class";
		private final String db_name = "mole.db";
		private ImageView imgv_Start, imgv_End ,rank;
		MediaPlayer player;
		Context ctx;
		TextView ranking[] = new TextView[30];
		LinearLayout lay[] = new LinearLayout[10];
		Ranking_Data helper;
		Cursor cursor;
		SQLiteDatabase db;
		String db_player;
		int db_score;
		RadioButton by2,by3,by4;
		RadioGroup radio;
		int checkRadio;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);

			helper = new Ranking_Data(this);
			helper.search_data();

			// 배경 음악
			player = MediaPlayer.create(this, R.raw.intro);
			player.setVolume(0.8f, 0.8f);
			player.setLooping(true);
			player.start();

			cursor = helper.getAll();
			Log.i(tag, "counter="+cursor.getCount());
			cursor.moveToFirst();
			if(cursor.getCount()>11){
				cursor.moveToLast();
				int delete = cursor.getInt(0);
				helper.delete(delete);
				cursor.close();
			}

			String dbPath = getApplicationContext().getDatabasePath(db_name).getPath();
			Log.i("my db path=", ""+dbPath);
			//각각의 아이디를 변수에 저장해줌
			imgv_Start = (ImageView) findViewById(R.id.start);
			imgv_End = (ImageView) findViewById(R.id.exit);
			rank = (ImageView) findViewById(R.id.rank);
			//ID가 저장된 변수가 눌려졌을 때 onClick함수로 주소값을 전달해 줌
			imgv_Start.setOnClickListener(this);
			imgv_End.setOnClickListener(this);
			rank.setOnClickListener(this);
		}
		@Override //이벤트 처리
		public void onClick(View v) {
			Log.i(tag, "v id="+v.getId());
			Cursor cur;
			cur = helper.getAll();

			switch (v.getId()) {
				case R.id.start:
					choice_state();
					break;
				case R.id.exit:
//			finish();
					System.exit(0);
					break;
				case R.id.rank:
					if(cur.getCount()>0){
						show_dig();
						break;
					}
					else if(cur.getCount()==0){
						Toast.makeText(Main.this, "등록 된 랭킹이 없습니다.", Toast.LENGTH_SHORT).show();
						break;
					}
			}
		}
		@SuppressLint("ResourceType")
		public void choice_state(){
			final LinearLayout lay = (LinearLayout)View.inflate(Main.this,R.layout.state,null);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Setting matrix");
			dialog.setView(lay);
			radio = (RadioGroup) lay.findViewById(R.id.radiogroup);
			by2 = (RadioButton) lay.findViewById(R.id.radio1);
			by3 = (RadioButton) lay.findViewById(R.id.radio2);
			by4 = (RadioButton) lay.findViewById(R.id.radio3);
			by2.setId(0); by3.setId(1); by4.setId(2);
			dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(by2.isChecked()){
						checkRadio = by2.getId();
					}
					else if(by3.isChecked()){
						checkRadio = by3.getId();
					}
					else if(by4.isChecked()){
						checkRadio = by4.getId();
					}
					setContentView(new Mole_View(Main.this,checkRadio));
					player.stop();
				}
			});

			dialog.show();
		}
		public void show_dig (){
			final LinearLayout lay = (LinearLayout)View.inflate(Main.this, R.layout.show_rank, null);
			AlertDialog.Builder ad = new AlertDialog.Builder(this);//빌더 객체 생성
			ad.setTitle("RANKING");//다이얼로그의 제목
			ad.setView(lay);//화면에 보여줄 대상을 설정

			ranking[0] = (TextView) lay.findViewById(R.id.rank1);
			ranking[1] = (TextView) lay.findViewById(R.id.rank2);
			ranking[2] = (TextView) lay.findViewById(R.id.rank3);
			ranking[3] = (TextView) lay.findViewById(R.id.rank4);
			ranking[4] = (TextView) lay.findViewById(R.id.rank5);
			ranking[5] = (TextView) lay.findViewById(R.id.rank6);
			ranking[6] = (TextView) lay.findViewById(R.id.rank7);
			ranking[7] = (TextView) lay.findViewById(R.id.rank8);
			ranking[8] = (TextView) lay.findViewById(R.id.rank9);
			ranking[9] = (TextView) lay.findViewById(R.id.rank10);
			Cursor c;
			c = helper.getAll_lineup();
			c.moveToFirst();//커서를 테이블 제일 처음, 즉 테이블의 제 1행을 가리키도록 한다.
			Log.i("dialog", "movefirst");
			for(int i=1;i<=c.getCount();i++){
				if(!c.isAfterLast()){//커서가 데이터의 마지막일때 까지 커서가 이동할 수있도록 해준다.
					Log.i("dialog", "while");
					db_player = c.getString(1);
					db_score = c.getInt(2);
					Log.i("dialog", "db_player = "+db_player+"   db_score = "+db_score);
					ranking[i].setText("  "+i+"등 "+db_player+" "+db_score+"점");
					c.moveToNext();//커서를 다음 행으로 이동시켜주는 역할
					Log.i(tag, "move next");
				}
				else if(c.isAfterLast()){
					c.close();
					break;
				}
			}
			ad.setNegativeButton("뒤로가기", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();//다이얼로그 종료
				}
			});
			ad.show();
		}//End of dialog
	}