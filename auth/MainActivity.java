package com.cyberkyj.firebase_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtEmail, edtPassword;
    TextView txtSignIn, txtMessage;
    FirebaseAuth auth;
    Button btnSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        txtSignIn = findViewById(R.id.viewSignIn);
        txtMessage = findViewById(R.id.textView2);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        if(auth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        btnSignUp = findViewById(R.id.btnSignUP);
        btnSignUp.setOnClickListener(this);
        txtSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnSignUp){
            registerUser();
        }else if(v ==txtSignIn){
            startActivity(new Intent(this, LoginActivity.class));

        }
    }

    public void registerUser(){

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Email을 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Password를 입력해주세요",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("등록중입니다. 기다려주세요..");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"등록 에러!",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
}
