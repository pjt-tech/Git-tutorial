package com.kye.mycinema.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kye.mycinema.R;

public class PreviewActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayer;
    TextView txt_preView;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        index = intent.getIntExtra("index",0);

        txt_preView = findViewById(R.id.txt_preView);
        youTubePlayerView = findViewById(R.id.youtubeView);
        Button btn_play = findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playYoutube(index);
            }
        });
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_preView.setText("예고편 감상 대기중..");
                finish();
            }
        });
    }

    private void playYoutube(final int index){
        youTubePlayerView.initialize("AIzaSyBojWazps51Ta2XOvuA5h_Ps2LG2J0-8No", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (index == 0) {
                    youTubePlayer.loadVideo("y422jVFruic");
                    youTubePlayer.play();
                    txt_preView.setText("예고편 감상중..!");
                } else if (index == 1) {
                    youTubePlayer.loadVideo("TK3u3QJOfXM");
                    youTubePlayer.play();
                    txt_preView.setText("예고편 감상중..!");
                } else if (index == 2) {
                    youTubePlayer.loadVideo("pxR6cKkPzNo");
                    youTubePlayer.play();
                    txt_preView.setText("예고편 감상중..!");
                } else if (index == 3) {
                    youTubePlayer.loadVideo("JvGr8n2MBps");
                    youTubePlayer.play();
                    txt_preView.setText("예고편 감상중..!");
                } else if (index == 4) {
                    youTubePlayer.loadVideo("HRkSk0-2XtQ");
                    youTubePlayer.play();
                    txt_preView.setText("예고편 감상중..!");
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


    }
}
