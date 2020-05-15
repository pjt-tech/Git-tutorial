package com.cyberkyj.catchme.framework;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundManager {
	private static SoundManager sound;
	private SoundPool soundpool;
	private HashMap hashmap;
	private AudioManager manager;
	private Context ctx;

	public static SoundManager getInstance(){
		if(sound==null){
			sound = new SoundManager();
		}
		return sound;
	}
	public void Init(Context context){
		soundpool = new SoundPool(6,AudioManager.STREAM_MUSIC,0);
		hashmap = new HashMap();
		manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		ctx = context;
	}

	@SuppressWarnings("unchecked")
	public void addSound(int _index,int soundID){
		int id = soundpool.load(ctx, soundID,1);
		hashmap.put(_index, id);
	}

	//사운드 재생
	public void play(int _index){
		float streamVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume/manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundpool.play((Integer)hashmap.get(_index), streamVolume, streamVolume, 1, 0, 1f);
	}

	//사운드 반복재생
	public void playLooped(int _index){
		float stream = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
		stream = stream/manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundpool.play((Integer)hashmap.get(_index),stream,stream,1,-1,1f);
	}
	public void setLoop(int streamID){
		soundpool.setLoop(streamID, -1);
	}
}