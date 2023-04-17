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

    // 선언
    EditText signup_email, signup_id, signup_name, signup_pw;
    Button complete_signup;
    DBManager dbManager = new DBManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        
        // 아이디 연결
        signup_email = (EditText) findViewById(R.id.signup_email); // 회원가입 시 사용할 이메일 입력 에디트텍스트
        signup_id = (EditText) findViewById(R.id.signup_id); // 회원가입 시 사용할 아이디 입력 에디트텍스트
        signup_name = (EditText) findViewById(R.id.signup_name); // 회원가입 시 사용자명 입력 에디트텍스트
        signup_pw = (EditText) findViewById(R.id.signup_pw); // 회원가입 시 사용할 비밀번호 입력 에디트텍스트
        complete_signup = (Button) findViewById(R.id.complete_signup); // 회원가입 진행 버튼

        // 사용자 입력 정보
        String email = signup_email.getText().toString();
        String id = signup_id.getText().toString();
        String username = signup_name.getText().toString();
        String password = signup_pw.getText().toString();

        // TODO : 데이터 연동하여 이메일 및 아이디 중복 체크 가능하도록
        // 이것 때문에 에뮬레이터 실행이 안 돼서 잠깐 주석처리 해두었습니다...
        /**
        // 이메일 중복 체크
        if (dbManager.checkEmailExist(email)) {
            Toast.makeText(this, "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
            complete_signup.setEnabled(false); // 이메일이 이미 사용 중일 시 회원가입 버튼 비활성화
            return;
        } else {
            complete_signup.setEnabled(true); // 사용 가능한 이메일일 시 회원가입 버튼 활성화
        }

        // 아이디 중복 체크
        if (dbManager.checkIdExist(id)) {
            Toast.makeText(this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
            complete_signup.setEnabled(false); // 아이디가 이미 사용 중일 시 회원가입 버튼 비활성화
            return;
        } else {
            complete_signup.setEnabled(true); // 사용 가능한 아이디일 시 회원가입 버튼 활성화
        }
         **/

        // 회원가입 진행 버튼 이벤트
        complete_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : 회원가입한 유저의 정보가 DB에 추가될 수 있도록
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
