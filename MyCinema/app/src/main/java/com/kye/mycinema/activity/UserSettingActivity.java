package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        //네비게이션드로어의 사용자 설정화면 로그인시 이용가능 미완성

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
                Toast.makeText(getApplicationContext(),"설정이 저장되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //회원 탈퇴 기능
        txt_revoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //삭제 전  AlertDialog로 사용자에게 한번 더 물어봄
                AlertDialog.Builder builder = new AlertDialog.Builder(UserSettingActivity.this);
                builder.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //확인 버튼 클릭 시 현재 로그인 된 사용자의 정보를 확인 후 탈퇴 시킴
                        FirebaseUser user = auth.getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(),"계정이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"취소하였습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
}
