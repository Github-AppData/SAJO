package com.example.main;

import static com.example.main.LoginActivity.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;

public class MypageActivity extends AppCompatActivity {

    TextView user_info_email_data, user_info_name_data;
    Button btn_wordbook, btn_change_password, btn_logout, btn_go_main;
    LinearLayout mypage_layout, wordbook_layout;
    DBHelper dbhelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        dbhelper = new DBHelper(MypageActivity.this,"userdata.db",null,1);
        db = dbhelper.getWritableDatabase();

        user_info_email_data = (TextView) findViewById(R.id.user_info_email_data) ; // 유저 이메일 정보
        user_info_name_data = (TextView) findViewById(R.id.user_info_name_data) ; // 유저명 정보
        btn_change_password = (Button) findViewById(R.id.btn_change_password_launch); // 비밀번호 변경 버튼
        btn_logout = (Button) findViewById(R.id.btn_logout); // 로그아웃 버튼
        btn_wordbook = (Button) findViewById(R.id.btn_to_wordbook); // 나의 단어장 버튼
        btn_go_main = (Button) findViewById(R.id.btn_go_main); // 메인으로 돌아가는 버튼


        mypage_layout = findViewById(R.id.mypage_layout);

        // 내 정보 텍스트뷰
        user_info_email_data.setText(user.getUser_email());
        user_info_name_data.setText(user.getUser_name());

        // 프래그먼트 레이아웃 추가
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WordbookFragActivity wordbookFragActivity = new WordbookFragActivity();
        fragmentTransaction.add(R.id.tab_word_frag, wordbookFragActivity);

        // 로그아웃 버튼 클릭 시 로그아웃 프로세스 진행
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 로그아웃 버튼 클릭 이벤트 처리
                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                builder.setMessage("정말 로그아웃 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // "예" 버튼 클릭 이벤트 처리
                                SharedPreferences sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("is_logged_in", false);
                                editor.apply();
                                // 저장되어 있던 데이터 null값으로 초기화
                                user.setUser_id("");
                                user.setUser_email("");
                                user.setUser_pwd("");
                                user.setUser_name("");
                                Intent intent = new Intent(MypageActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(MypageActivity.this, "로그아웃했습니다.", Toast.LENGTH_SHORT).show();
                                finish(); // 이전 화면을 종료하여 뒤로 가기 버튼으로 이전 화면으로 돌아갈 수 없도록 함
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // 나의 단어장 버튼 클릭 이벤트 (나의 단어장으로 이동)
        btn_wordbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_wd = new Intent(MypageActivity.this, WordbookActivity.class);

                startActivity(intent_wd); // 나의 단어장 화면으로 이동함

                /*
                com.example.main.WordbookFragActivity wordFragment = com.example.main.WordbookFragActivity.newInstance("value1", "value2");
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.wordbook_frame, wordFragment);
                transaction.commit();

                Intent wordbook_intent = new Intent(MypageActivity.this, WordbookActivity.class);
                startActivity(wordbook_intent); // 액티비티를 실행합니다.*/


            }
        });

        // 비밀번호 변경 버튼 클릭 시 비밀번호 변경 화면으로 이동
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_pw = new Intent(MypageActivity.this, ChangePasswordActivity.class);
                startActivity(intent_pw); // 비밀번호 변경 화면으로 이동함
            }
        });

        // BACK 버튼 클릭 이벤트 (메인으로 이동)
        btn_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_main = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent_main);
            }
        });



    }


}
