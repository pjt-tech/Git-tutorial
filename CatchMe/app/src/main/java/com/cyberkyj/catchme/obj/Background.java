package com.cyberkyj.catchme.obj;

import android.graphics.Bitmap;

import com.cyberkyj.catchme.framework.GraphicObject;


public class Background {
	public static Bitmap background;

	public Background(GraphicObject graphic){
		graphic.drawBitmap(background, 0, 0);
	}

}
