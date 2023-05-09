package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 선언
    Button btn_chart, btn_study, btn_mypage;
    LinearLayout chart_layout, study_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login에서 보낸 데이터 받음
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        // 프래그먼트 레이아웃 추가
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // ChartFragActivity 추가
        ChartFragActivity chartFragActivity = new ChartFragActivity();
        fragmentTransaction.add(R.id.tab_chart_frag, chartFragActivity);

        // StudyFragActivity 추가
        StudyFragActivity studyFragActivity = new StudyFragActivity();
        fragmentTransaction.add(R.id.tab_study_frag, studyFragActivity);

        // transaction 실행
        fragmentTransaction.commit();

        // 아이디 부여
        btn_chart = (Button) findViewById(R.id.btn_chart); // 차트 레이아웃을 띄우는 버튼
        btn_study = (Button) findViewById(R.id.btn_study); // 스터디 레이아웃을 띄우는 버튼
        btn_mypage = (Button)findViewById(R.id.btn_mypage); // 마이페이지로 가는 버튼

        chart_layout = (LinearLayout) findViewById(R.id.chart_layout); // 차트 레이아웃
        study_layout = (LinearLayout) findViewById(R.id.study_layout); // 스터디 레이아웃


        // 차트 버튼 클릭 시 색상 변경 및 레이아웃 변경
        btn_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.isClickable()) {
                    btn_chart.setBackgroundColor(Color.CYAN);
                    btn_chart.setTextColor(Color.BLACK);
                    btn_study.setBackgroundColor(Color.parseColor("#0C2840"));
                    btn_study.setTextColor(Color.WHITE);
                    chart_layout.setVisibility(View.VISIBLE);
                    study_layout.setVisibility(View.GONE);
                }
            }
        });

        // 스터디 버튼 클릭 시 색상 변경 및 레이아웃 변경
        btn_study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.isClickable()) {
                    btn_study.setBackgroundColor(Color.CYAN);
                    btn_study.setTextColor(Color.BLACK);
                    btn_chart.setBackgroundColor(Color.parseColor("#0C2840"));
                    btn_chart.setTextColor(Color.WHITE);
                    chart_layout.setVisibility(View.GONE);
                    study_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        // 마이페이지 버튼 클릭 이벤트 (마이페이지로 이동)
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 마이 페이지 액티비티로 이동하는 Intent를 생성합니다.
                Intent mypage_intent = new Intent(MainActivity.this, MypageActivity.class);
                mypage_intent.putExtra("id",id);
                startActivity(mypage_intent); // 액티비티를 실행합니다.
            }
        });

    }

    // 아래로는 프래그먼트 클래스 연결 부분
    public static class ChartFragActivity extends Fragment {

        private ListView listView; // CHART 창의 프래그먼트로 연결된 리스트뷰

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // chart 레이아웃 파일 내용이 main 레이아웃 파일의 프래그먼트 안으로
            View ChartView = inflater.inflate(R.layout.fragment_chart_frag_activity, container, false);

            // 리스트뷰 아이디 부여
            listView = ChartView.findViewById(R.id.song_list);

            // 임시 노래 목록
            String[][] data = {
                    {"1", "첫번째 댄스곡 제목", "1위 가수"},
                    {"2", "두번째 발라드 제목", "비운의 가수"},
                    {"3", "세번째 힙합 제목", "그냥 그런 가수"}
            };
            // TODO : 위에 노래 데이터 넣기

            List<String[]> songList = Arrays.asList(data);

            // SongAdapter 객체를 생성하고 ListView의 어댑터로 설정
            SongAdapter Sadapter = new SongAdapter(getActivity(), songList);
            listView.setAdapter(Sadapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    
                    // TODO : Chart 에서 노래 클릭 시 Study 탭으로 해당 노래를 추가할 수 있도록 하는 로직
                    
                }
            });

            return ChartView;

        }

    }

    public static class StudyFragActivity extends Fragment {

        private ListView playlist_view; // STUDY 창의 프래그먼트로 연결된 리스트뷰

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // study 레이아웃 파일 내용이 main 레이아웃 파일의 프래그먼트 안으로
            View StudyView = inflater.inflate(R.layout.fragment_study_frag_activity, container, false);

            // 리스트뷰 아이디 부여
            playlist_view = StudyView.findViewById(R.id.playlist_view);

            // TODO : Chart 화면에서 클릭한 아이템 데이터 끌어올 수 있도록 수정
            String[][] data = {};

            List<String[]> playList = Arrays.asList(data);

            // PlaylistAdapter 객체를 생성하고 Listview의 어댑터로 설정
            PlaylistAdapter Padapter = new PlaylistAdapter(getActivity(), playList);
            playlist_view.setAdapter(Padapter);

            return StudyView;
        }

    }

}

