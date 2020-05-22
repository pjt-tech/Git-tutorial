package com.kye.mycinema.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kye.mycinema.R;

public class UserSettingActivity extends AppCompatActivity {

    TextView txt_mail,txt_pw,txt_revoke;
    Button btn_in,btn_out;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        txt_mail = findViewById(R.id.txt_mail);
        txt_pw = findViewById(R.id.txt_pw);
        txt_revoke = findViewById(R.id.txt_revoke);

        txt_mail.setText(user.getEmail());

        btn_in = findViewById(R.id.btn_in);
        btn_out = findViewById(R.id.btn_out);


        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
