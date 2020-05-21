package com.cyberkyj.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogOut;
    TextView txtDelete;
    TextView userEmail;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btnLogOut = findViewById(R.id.btnLogOut);
        txtDelete = findViewById(R.id.txtDelete);
        userEmail = findViewById(R.id.txtUserEmail);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = auth.getCurrentUser();
        userEmail.setText("반갑습니다. "+user.getEmail()+"으로 로그인하였습니다.");
        btnLogOut.setOnClickListener(this);
        txtDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btnLogOut){
            auth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else if(v==txtDelete){
            AlertDialog.Builder alert_Confirm = new AlertDialog.Builder(ProfileActivity.this);
            alert_Confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseUser user = auth.getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getApplicationContext(),"계정이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                        }
                    });
            alert_Confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"취소하였습니다",Toast.LENGTH_SHORT).show();

                }
            });
            alert_Confirm.show();
        }
    }
}
