package com.example.main;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    // 변수 선언
    EditText signup_email, signup_id, signup_name, signup_pw;
    Button complete_signup;
    DBManager dbManager = new DBManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        // 아이디 연결
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_id = (EditText) findViewById(R.id.signup_id);
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_pw = (EditText) findViewById(R.id.signup_pw);
        complete_signup = (Button) findViewById(R.id.complete_signup);

        // 사용자 입력 정보
        String email = signup_email.getText().toString();
        String id = signup_id.getText().toString();
        String username = signup_name.getText().toString();
        String password = signup_pw.getText().toString();

        /**
        // 이메일 중복 체크
        if (dbManager.checkEmailExist(email)) {
            Toast.makeText(this, "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
            complete_signup.setEnabled(false);
            return;
        } else {
            complete_signup.setEnabled(true);
        }

        // 아이디 중복 체크
        if (dbManager.checkIdExist(id)) {
            Toast.makeText(this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
            complete_signup.setEnabled(false);
            return;
        } else {
            complete_signup.setEnabled(true);
        }
         **/

        complete_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리
                dbManager.addUser(email, id, username, password);
                Context SignupActivity = null;
                Toast.makeText(SignupActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                // 회원가입 성공 시 로그인 화면으로 이동
                Intent intent_loginagain = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent_loginagain);
                finish();
            }
        });
    }
}
