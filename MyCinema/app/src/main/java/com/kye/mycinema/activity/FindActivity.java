package com.kye.mycinema.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kye.mycinema.R;

public class FindActivity extends AppCompatActivity {

    EditText edt_mail;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        //암호찾기 화면

        edt_mail = findViewById(R.id.edt_name);
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        Button btn_in = findViewById(R.id.btn_in);
        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("E-mail 발송중입니다..");
                progressDialog.show();
                String mail = edt_mail.getText().toString();

                //auth에 sendPasswordResetEmail로 사용자의 메일에 해당하는 비밀번호를 발송
                auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"E-mail 발송이 완료되었습니다.",Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"E-mail 발송 실패.",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}
