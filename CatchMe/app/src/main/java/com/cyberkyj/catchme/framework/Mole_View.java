package com.cyberkyj.catchme.framework;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyberkyj.catchme.R;
import com.cyberkyj.catchme.database.Ranking_Data;
import com.cyberkyj.catchme.obj.Background;
import com.cyberkyj.catchme.obj.Mole;
import com.cyberkyj.catchme.obj.PlayNpause;

import java.util.ArrayList;


public class Mole_View extends SurfaceView implements SurfaceHolder.Callback, Runnable{

	private final String tag = "view class";

	/*Database 변수*/
	//DatabaseFile의 경로를 가져오기위한 변수
	Ranking_Data helper;
	Cursor cur;
	SQLiteDatabase db;

	//System 변수
	private SurfaceHolder holder;
	private Thread thread;
	private GraphicObject graphic;
	private Context ctx;
	private PlayNpause pnp;

	//장면
	private final static int
			Title = 0,
			Play = 1,
			GameOver = 2;

	//Game 변수
	private ArrayList<Mole> m;
	private Background back;
	private int init = Title;
	private int scene;
	private int score;

	private long before_endTime;		//resume시 시간을 맞춰주기위해 이전 게임시간을 저장
	private long endTime;				//게임시간
	private long comboTime;				//두더지를 잡았을 때의 시간 저장
	private long pauseTime;				//일시정지 시간
	private int GameTime = 20000;		//게임시간
	static int settime = 2500;			//콤보 시간(combotime + settime) 1콤보일때 2콤보를 하려면 2.5초안에 눌러야함.

	private static int combo;
	private static int MatRix;			//처음 두더지 갯수를 세팅 해줄 변수(mainActivity로 부터 라디오 아이디값을 넘겨받음)
	private int playNpause = 0;			//0일때 일시정지 그림, 1일때 재생그림 표시
	private boolean isRunning = false;	//일시정지시 반복문을 탈출하기 위한 변수

	int StringPosition;
	String show_combo;
	int StringCombo;
	int combo_score=10;
	int width;							//화면 가로
	int height;							//화면 세로
	static int j=1;		//combo_score 에 곱해지는 수(1,2,4,8,16...으로 점점 커짐)
	int increase=0; 	//combo의 글씨 크기를 점점 키워주기위한 변수

	/*사운드 변수*/
	private SoundManager S_Manager;
	public static final int
			Hit = 1,
			Hit2 = 2,
			Item = 3,
			CountDown = 4,
			Lose = 5;
	int soundCheck=0;
	MediaPlayer player;

	//생성자
	public Mole_View(Context context,int RadioId) {
		super(context);

		m = new ArrayList<Mole>();
		this.ctx = getContext();

		MatRix = RadioId;
		setEffectSound();
		setGameImage();
		setBGM();

		helper = new Ranking_Data(context);
		holder = getHolder();
		holder.addCallback(this);
		graphic = new GraphicObject(holder);

//		setFocusable(true); //Activity에서는 Key이벤트가 모두 먹히는데 View에서는 해당 View가 Focus 될 때만 동작
	}

	//표면 변경
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {

	}

	//표면 생성
	public void surfaceCreated(SurfaceHolder holder) {
		Resources res=getResources();

		pnp = new PlayNpause(getWidth()/2-30,30);
		pnp.pause = BitmapFactory.decodeResource(res,R.drawable.pausebtn);
		pnp.resume = BitmapFactory.decodeResource(res,R.drawable.playbtn);
		thread = new Thread(this);
		width = getWidth();
		height = getHeight();
		Log.v(tag, "width="+width+" height="+height);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread = null;
		player.stop();
	}

	@SuppressWarnings("static-access")
	public void run(){
		String str;
		while(thread!=null){
			while(!isRunning){
				if (init>=0) {
					scene=init;
					init=-1;

					if (scene==Title) {
						score=0;
						combo=0;
						StringCombo=0;

						//두더지 출력 위치
						m =new ArrayList<Mole>();
						int getX = getWidth();
						int getY = getHeight();

						//2 by 2
						if(MatRix==0){
							for(int i=1;i<=2;i++){
								for(int j=1;j<=4;j+=2){
									m.add(new Mole(( (getX/5)*j)/*-40*/, (getY/4*i)+50) );
								}
							}
						}

						//3 by 3
						else if(MatRix==1){
							for(int i=1;i<=3;i++){//행
								for(int j=1;j<=5;j+=2){//열
									m.add(new Mole( (getX/8)*j , (getY/5*i) ));
								}
							}
						}

						//4 by 4
						else if(MatRix==2){
							for(int i=1;i<=4;i++){
								for(int j=1;j<=22;j+=7){
									m.add(new Mole( (getX/30)*j,(getY/5*i) ));
								}
							}
						}
					}
					// 플레이의 초기화
					else if (scene==Play) {/*  1/1000 초   */
						endTime=System.currentTimeMillis()+GameTime;
						Log.e("초기화","endTime="+endTime);
						before_endTime=0;
						comboTime=0;
						pauseTime=0;
						playNpause=0;
						soundCheck=0;

					}
					// 게임 오버의 초기화
					else if (scene==GameOver) {
						endTime=System.currentTimeMillis()+1000;
						combo=0;
						j=1;
						settime = 2500;
						increase = 0;
					}
				}

				//더블 버퍼링
				graphic.lock();
				//배경화면 그려주기
				try{
					graphic.drawBitmap(back.background, 0, 0);

				}catch(Exception ex){

				}

				//일시정지 그리기
				pnp.draw(graphic, playNpause);
				//일시정지
				if(playNpause==1){
					isRunning=true;
				}

				// 두더지 그리기
				for (int i=0;i<m.size();i++) {
					if (scene==Play) {
						m.get(i).state();//프레임 증가
					}
					m.get(i).draw(graphic);
				}

				// 점수 표시
				graphic.Color(Color.rgb(255,0,0));
				graphic.FontSize(width/20);
				str="Score";
				graphic.drawString(str,width/18,height/34);

				if(score<10){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/11,height/12);
				}
				else if (score<100){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/13,height/12);
				}
				else if (score<1000){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/18,height/12);
				}
				else if (score<10000){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/27,height/12);
				}
				else if (score<100000){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/54,height/12);
				}
				else if (score<1000000){
					str=""+score;
					graphic.FontSize(width/14);
					graphic.drawString(str,width/60,height/12);
				}
				else{
					graphic.FontSize(width/14);
					graphic.drawString(""+score,width/11,height/12);
				}

				/*시간표시*/
				graphic.Color(Color.rgb(40,40,125));
				graphic.FontSize(width/20);
				str="Time";
				graphic.drawString(str,width/2+230,height/34);


				graphic.FontSize(width/14); //시간의 크기(00:00)

				int time=(int)(endTime-System.currentTimeMillis())/1000;

				if (scene!=Play) {
					str="00:00";
				}
				else if (time<=0) {
					str="00:00";
					init=GameOver;
				}
				else if (time<10) {
					str="00:0"+time;
					if(time==7){
						if(soundCheck==0){
							S_Manager.play(CountDown);
							soundCheck=1;
						}
					}
				}
				else {
					str="00:"+time;
				}
				graphic.drawString(str,width/2+210,height/12);//시간의 위치(00:00)
				/*시간표시 끝*/


				// 메시지 표시
				if (scene==Title || scene==GameOver) {

					if (scene==Title){
						str="Ready??";
						StringPosition=4;
					}
					if (scene==GameOver){
						str="Game Over";
						StringPosition=6;
					}
					graphic.Color(Color.rgb(255,0,0));
					graphic.FontSize(width/7);
					graphic.drawString(str,width/StringPosition,height/2-10);
				}

				//수정중

				if(combo>0 && scene==Play){
					//1초 동안 콤보 글자 띄어주기
					if (comboTime + settime - System.currentTimeMillis()<0){
						combo=0;
						j=1;
						settime = 2500;
						increase = 0;
					}
					if((comboTime+1000)-(System.currentTimeMillis())>0){

						if(increase>=0){
							show_combo="COMBO!!";
							graphic.FontSize(width/18+increase);
						}
						graphic.Color(Color.rgb(255,255,0));
						if(StringCombo>=10){
							graphic.drawString(show_combo,(width/2)-70,200);
						}
						else{
							graphic.drawString(show_combo,(width/2)-50,200);
						}

						if(increase>=0){
							show_combo=""+StringCombo;
							graphic.FontSize(width/10+increase);
						}
						graphic.Color(Color.rgb(114,100,100));
						if(StringCombo>=10){
							graphic.drawString(show_combo,(width/2)-130,200);
						}
						else{
							graphic.drawString(show_combo,(width/2)-100,200);
						}
						increase+=2;
					}

				}
				graphic.unlock(); //더블버퍼 종료
			}
		}
		try{Thread.sleep(1000);}catch(Exception e){}
	}//End of run

	private void setEffectSound(){
		S_Manager = SoundManager.getInstance();
		S_Manager.Init(ctx);

		S_Manager.addSound(Hit, R.raw.hit);
		S_Manager.addSound(Hit2, R.raw.hit1);
		S_Manager.addSound(Item, R.raw.item);
		S_Manager.addSound(CountDown, R.raw.countdown);
		S_Manager.addSound(Lose, R.raw.lose);

	}

	@SuppressWarnings("static-access")
	private void setGameImage(){
		Resources res = getResources();
		back.background = BitmapFactory.decodeResource(res, R.drawable.background);
		Mole.bitmap[0] = BitmapFactory.decodeResource(res,R.drawable.mole_dig);
		Mole.bitmap[1] = BitmapFactory.decodeResource(res,R.drawable.mole_nomal);
		Mole.bitmap[2] = BitmapFactory.decodeResource(res,R.drawable.mole_hit);
		Mole.bitmap[3] = BitmapFactory.decodeResource(res,R.drawable.item);
		Mole.bitmap[4] = BitmapFactory.decodeResource(res,R.drawable.buddha);
	}

	private void setBGM(){
		// 배경 음악
		player = MediaPlayer.create(ctx, R.raw.start);
		player.setVolume(0.5f, 0.5f);
		player.setLooping(true);
		player.start();

	}

	public boolean onTouchEvent(MotionEvent event){
		int touchX=(int) event.getX();
		int touchY=(int) event.getY();
		int touchAction = event.getAction();
		if(touchAction == MotionEvent.ACTION_DOWN){
			if(scene==Title){
				init=Play;
			}
			else if(scene == Play){
				for(int i=0;i<m.size();i++){
					//일반 두더지
					if(m.get(i).isHit(touchX, touchY)){
						increase =0;
						combo++;
						if(combo==1){
							S_Manager.play(Hit);
						}
						else if(combo>=2){
							S_Manager.play(Hit2);
						}
						StringCombo=combo;
						comboTime = System.currentTimeMillis();//
						score+=(combo_score*j);//(10*(1,2,4,8,16,32,64...))
						j*=2;
						if(settime>=2000){
							settime -= 250;
						}
						else if(settime>=1400){
							settime -= 200;
						}
						else if(settime>=1000){
							settime-=100;
						}
						else if(settime>=800){
							settime-=50;
						}
						else if(settime>=600){
							settime-=20;
						}
						else if(settime<600){
							settime-=10;
						}
						else if(settime<=0){
							settime=580;
						}
					}

					//아이템
					else if(m.get(i).get_item(touchX, touchY)){
						score+=50;
						S_Manager.play(Item);
					}
					//부처캐릭터 클릭시
					else if(m.get(i).lose_score(touchX, touchY)){
						score-=80;
						combo=0;
						j=1;
						settime = 2500;
						increase = 0;
						S_Manager.play(Lose);
					}
				}

				//일시정지버튼클릭
				if(pnp.isClick(touchX, touchY)){
					//홀짝..
					//재생상태 일시정지 시켜줌
					if(playNpause==0){
						playNpause = 1;
						pauseTime = System.currentTimeMillis();
						before_endTime = endTime;
						pause_dig();
					}
				}
			}
			else if(scene==GameOver){
				if(endTime<System.currentTimeMillis()){
					surfaceCreated(holder);
					Cursor c;
					c = helper.getAll();
					c.moveToFirst();

					//등록된 점수들이 있고 현재 점수가 0점보다 클때
					if((score > 0) && (c.getCount() > 10) ){
						//점수비교
						while(!c.isAfterLast()){
							//현재 점수가 등록된 점수들 보다 컷을때 등록함.
							if(score>c.getInt(2)){
								insert_dig(score);
								break;
							}
							//현재 점수가 등록된 점수들 보다 낮을때 등록안함.
							else if(score<c.getInt(2)){
								goMain_dig();
								break;
							}
							c.moveToNext();
						}
					}
					//10등까지 등록이 안되고 점수가 0보다 크면 점수등록을 해준다.
					else if(c.getCount() <=10 && score > 0){
						insert_dig(score);
					}
					//점수가 0점이면 등록안해줌
					else if(score <= 0){
						goMain_dig();
					}

				}
			}
		}
		return true;
	}//End of Touch


	public void pause_dig(){

		final LinearLayout lay = (LinearLayout)View.inflate(ctx,R.layout.pause,null);
		AlertDialog.Builder ad = new AlertDialog.Builder(ctx);
		ad.setTitle("PAUSE!!");
		ad.setView(lay);
		ad.setPositiveButton("resume", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				isRunning = false;
				playNpause = 0;
				endTime = System.currentTimeMillis() + (before_endTime - pauseTime);
			}
		});
		ad.setNegativeButton("Go To Main", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(ctx, Main.class);
				ctx.startActivity(intent);
				graphic.lock();
				thread=null;
			}
		});

		ad.show();

	}
	public void insert_dig(final int score1){
		final LinearLayout lay = (LinearLayout)View.inflate(ctx,R.layout.insert_rank,null);
		AlertDialog.Builder ad = new AlertDialog.Builder(ctx);
		ad.setTitle("랭킹을 등록하세요");
		ad.setView(lay);
		final EditText input_player = (EditText)lay.findViewById(R.id.input_player);


		//수정 버튼이 눌렸을때 처리하는 명령어
		ad.setPositiveButton("등록하기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(input_player.length()>=2){
					helper.add(input_player.getText().toString().trim(), score1);
					goMain_dig();
				}
				else{
					Toast.makeText(ctx,"2글자 이상 입력하세요", Toast.LENGTH_SHORT).show();
				}
			}});
		ad.setNegativeButton("등록하지 않기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				goMain_dig();
			}});
		ad.show();
	}//End of dialog

	public void goMain_dig(){
		final LinearLayout lay = (LinearLayout) View.inflate(ctx, R.layout.gotomain,null);
		AlertDialog.Builder ad = new AlertDialog.Builder(ctx);
		ad.setTitle("Continue??");
		ad.setView(lay);
		//수정 버튼이 눌렸을때 처리하는 명령어
		ad.setPositiveButton("메인으로", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(ctx, Main.class);
				ctx.startActivity(intent);
				graphic.lock();
				thread=null;
			}});
		ad.setNegativeButton("다시하기", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				init=0;
//				surfaceCreated(holder);
			}});

		ad.show();
	}//End of dialog

}