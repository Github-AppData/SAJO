package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class WordbookActivity extends AppCompatActivity {

    // 선언
    Button btn_mypage_back;
    Button btn_word;
    LinearLayout wordbook_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordbook_ui); // TODO : 수정

        // Mypage에서 보낸 데이터 받음
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        WordbookFragActivity wordbookFragActivity = new WordbookFragActivity();
        fragmentTransaction.add(R.id.tab_word_frag, wordbookFragActivity);

        // back 버튼 아이디 부여
        btn_mypage_back = (Button) findViewById(R.id.btn_mypage_back); // BACK 버튼
        btn_word = (Button) findViewById(R.id.btn_word); // BACK 버튼
        wordbook_layout = findViewById(R.id.wordbook_layout);

        btn_mypage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_mp = new Intent(WordbookActivity.this, MypageActivity.class);
                startActivity(intent_mp);
            }
        });

        // Word 버튼 클릭 시
        btn_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_word.setBackgroundColor(Color.CYAN);
                btn_word.setTextColor(Color.BLACK);
            }
        });

    }
    public static class WordbookFragActivity extends Fragment {

        private ListView listView; // Wordbook 창의 프래그먼트로 연결된 리스트뷰

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            // chart 레이아웃 파일 내용이 main 레이아웃 파일의 프래그먼트 안으로
            View WordView = inflater.inflate(R.layout.fragment_wordbook_frag_activity, container, false);

            // 리스트뷰 아이디 부여
            listView = WordView.findViewById(R.id.wordbook_list);


            // SongAdapter 객체를 생성하고 ListView의 어댑터로 설정

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    // TODO : Chart 에서 노래 클릭 시 Study 탭으로 해당 노래를 추가할 수 있도록 하는 로직
                    Toast.makeText(requireContext(), "아이디가 존재않습니다.", Toast.LENGTH_SHORT);
                }
            });

            return WordView;

        }

    }

}
