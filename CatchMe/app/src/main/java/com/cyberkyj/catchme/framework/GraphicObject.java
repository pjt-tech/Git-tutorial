package com.cyberkyj.catchme.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;


public class GraphicObject {
	private SurfaceHolder holder;
	private Paint paint;
	private Canvas canvas;

	
	public GraphicObject(SurfaceHolder holder){
		this.holder = holder;
		paint = new Paint();
		paint.setAntiAlias(true);
	}
	
	public void lock(){
		canvas = holder.lockCanvas();
	}
	
	public void unlock(){
		holder.unlockCanvasAndPost(canvas);
	}

	public void drawBitmap(Bitmap bitmap,int x, int y){
		canvas.drawBitmap(bitmap, x, y, null);
	}

	public void drawString(String string, int x, int y){
		try{
			canvas.drawText(string, x, y, paint);
		}catch(Exception ex){
			
		}
	}
	
	public void Color(int color){
		paint.setColor(color);
	}
	
	public void FontSize(int fontsize){
		paint.setTextSize(fontsize);
		
	}

}