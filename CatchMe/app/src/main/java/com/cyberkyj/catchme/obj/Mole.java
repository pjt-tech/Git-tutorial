package com.cyberkyj.catchme.obj;

import android.graphics.Bitmap;


import com.cyberkyj.catchme.framework.GraphicObject;

import java.util.Random;

public class Mole {
	Random Item_Random = new Random();
	Random State_Random = new Random();
	public static Bitmap[] bitmap = new Bitmap[5];
	private int state,itemrand,staterand;
	int x,y;
	public Mole(int x, int y){
		this.x = x;
		this.y = y;
		state = -State_Random.nextInt(150/*300*/);

	}

	public boolean isHit(int touchX, int touchY){
		if(0 < state && state < 100 && x <= touchX && touchX<= x + 250 && y <= touchY && touchY<= y + 250){
			//50% 확률임
			itemrand = Item_Random.nextInt(100);
			if(itemrand >= 60){ //60프로 확률로 아이템 드랍
				state = 65;
			}
			else{
				state = 35;
			}
			return true;
		}
		return false;
	}

	//아이템
	public boolean get_item(int touchX,int touchY){
		if( 64 < state && state < 95 && x <= touchX && touchX<= x + 100 && y <= touchY && touchY<= y + 100){
			state = 124;
			return true;
		}
		return false;
	}

	//부처님
	public boolean lose_score(int touchX,int touchY){
		if( 95 < state && state < 125 && x <= touchX && touchX<= x + 250 && y <= touchY && touchY<= y + 250){
			state = 124;
			return true;
		}
		return false;
	}

	public void state(){
		state++;//state값이 매 프레임마다 증가

		if(state == 35 || state == 64 || state == 95 |state == 124){
			state = -State_Random.nextInt(150/*300*/);
		}
		else if(state==-1){
			//확률로 부처님
			staterand = State_Random.nextInt(100);
			if(staterand%6==0){
				state=95;
			}
		}

	}

	//Mole Draw
	public void draw(GraphicObject graphic){
		int cnt=0;   // state == 난수

		if(state<0){ //숨어있는 두더지
			cnt=0;
		}
		else if(state < 35){//나온상태
			cnt = 1;
		}
		else if(state < 64){//맞은상태
			cnt = 2;
		}
		else if(state < 95){//아이템
			cnt = 3;
		}
		else if(state < 125){//부처님
			cnt = 4;
		}

		try{
			graphic.drawBitmap(bitmap[cnt], x, y);
		}catch(Exception ex){

		}
	}
}