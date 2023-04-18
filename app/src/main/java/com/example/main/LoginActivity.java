package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    // 선언
    private List<String> user_id = Arrays.asList("admin", "user1", "user2"); // 임시 유저 아이디 목록 (추후 삭제)
    private List<String> user_pw = Arrays.asList("admin", "password1", "password2"); // 임시 유저 비밀번호 목록 (추후 삭제)
    EditText login_id, login_pw;
    Button btn_login, btn_signup;
    Intent intent_main;
    Intent intent_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 아이디 부여
        login_id = (EditText)findViewById(R.id.login_id); // 로그인 시 입력할 아이디 에디트텍스트
        login_pw = (EditText)findViewById(R.id.login_pw); // 로그인 시 입력할 비밀번호 에디트텍스트
        btn_login = (Button)findViewById(R.id.btn_login); // 버튼을 눌러 로그인
        btn_signup = (Button)findViewById(R.id.btn_signup); // 회원가입창으로 이동하는 버튼

        // 로그인 이벤트
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = login_id.getText().toString();
                String password = login_pw.getText().toString();

                int index_id = user_id.indexOf(username);
                int index_pw = user_id.indexOf(password);

                // TODO : DB와 연동하여 아이디 및 비밀번호 검사 로직 수정
                if (username.equals(user_id.get(index_id)) && password.equals(user_pw.get(index_pw)))  {
                    // 일치하는 경우 메인 액티비티로 이동
                    intent_main = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent_main);
                } else {
                    // 로그인에 실패하면 토스트 메시지 표시
                    Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 비회원을 위한 회원가입 창으로 이동 이벤트
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 창으로 이동하는 코드 작성
                intent_signup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent_signup);
            }
        });

    }

}
