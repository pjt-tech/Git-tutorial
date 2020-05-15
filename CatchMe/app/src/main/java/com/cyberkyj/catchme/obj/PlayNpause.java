package com.cyberkyj.catchme.obj;

import android.graphics.Bitmap;

import com.cyberkyj.catchme.framework.GraphicObject;


public class PlayNpause {
	int x,y;
	public Bitmap pause, resume;
	public PlayNpause(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean isClick(int touchX,int touchY){
		if(x<= touchX && touchX<= x+72 && y<= touchY && touchY<= y+72){
			return true;
		}
		return false;
	}
	
	public void draw(GraphicObject graphic, int playNpause){
		if(playNpause==0){
			try{
				graphic.drawBitmap(pause, x, y);
			}catch(Exception ex){
			
			}
		}
		else if(playNpause==1){
			try{
				graphic.drawBitmap(resume, x, y);
			}catch(Exception ex){
			
			}
		}
	}
	
}
