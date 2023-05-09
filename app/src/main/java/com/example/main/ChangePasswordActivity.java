package com.example.main;

import static com.example.main.LoginActivity.user;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    // 선언
    EditText current_password, change_password, change_password_Check;
    Button btn_change_password_launch, btn_go_back;
    SQLiteDatabase db;
    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        // 아이디 부여
        current_password = (EditText) findViewById(R.id.current_password); // 현재 비밀번호 에디트텍스트
        change_password = (EditText) findViewById(R.id.change_password); // 변경할 비밀번호 에디트텍스트
        change_password_Check = (EditText) findViewById(R.id.change_password_check); // 변경할 비밀번호 확인 에디트텍스트
        btn_change_password_launch = (Button) findViewById(R.id.btn_change_password_launch); // 비밀번호 변경 실행 버튼
        btn_go_back = (Button) findViewById(R.id.btn_go_back); // 마이페이지로 돌아가는 back 버튼

        // 비밀번호 변경하기 버튼 클릭 시 이베트
        btn_change_password_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper = new DBHelper(ChangePasswordActivity.this,"userdata.db",null,1);
                db = dbhelper.getWritableDatabase();
                String Ecurrent_password = current_password.getText().toString();
                String Echange_password = change_password.getText().toString();
                String Echange_password_Check = change_password_Check.getText().toString();

                // 현재 비밀번호와 새로운 비밀번호 1, 2가 모두 입력되었는지 확인
                if (Ecurrent_password.isEmpty() || Echange_password.isEmpty() || Echange_password_Check.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "모든 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 새로운 비밀번호 1과 2가 일치하는지 확인
                else if (!Echange_password.equals(Echange_password_Check)) {
                    Toast.makeText(getApplicationContext(), "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 현재 비밀번호가 맞는지 확인
                else if (!Ecurrent_password.equals(user.getUser_pwd())) {
                    Toast.makeText(getApplicationContext(), "현재 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    // 비밀번호 변경 로직 부분
                    user.setUser_pwd(Echange_password);
                    dbhelper.Update(db,"USER",user.getUser_id(),user.getUser_name(),user.getUser_pwd(),user.getUser_email());
                    // 비밀번호 변경 후 로그인 화면으로 이동
                    Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish(); // 이전 화면을 종료하여 뒤로 가기 버튼으로 이전 화면으로 돌아갈 수 없도록 함
                }
                db.close();
            }
        });

        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_mp = new Intent(ChangePasswordActivity.this, MypageActivity.class);
                startActivity(intent_mp); // 다시 마이페이지로 돌아감
            }
        });
    }
}
