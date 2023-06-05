package com.example.main;

import android.content.Intent;
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
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // 선언
    Button btn_chart, btn_study, btn_mypage;
    LinearLayout chart_layout, study_layout;
    private static final String TAG = "DBSongHelper";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static com.example.main.ChartFragActivity newInstance(String param1, String param2) {
        com.example.main.ChartFragActivity fragment = new com.example.main.ChartFragActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static com.example.main.PlaylistFragActivity newInstance2(String mparam1, String mparam2) {
        com.example.main.PlaylistFragActivity fragment = new com.example.main.PlaylistFragActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mparam1);
        args.putString(ARG_PARAM2, mparam2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login에서 보낸 데이터 받음
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Log.d(TAG, "DB_PATH: ");


        // 프래그먼트 레이아웃 추가
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // ChartFragActivity 추가
        ChartFragActivity chartFragActivity = new ChartFragActivity();
        fragmentTransaction.add(R.id.tab_chart_frag, chartFragActivity);

        /*// WordbookFragActivity 추가
        WordbookFragActivity wordbookFragActivity = new WordbookFragActivity();
        fragmentTransaction.add(R.id.tab_word_frag, wordbookFragActivity);*/

        PlaylistFragActivity playlistFragActivity = new PlaylistFragActivity();
        fragmentTransaction.add(R.id.tab_playlist_frag, playlistFragActivity);

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


                    com.example.main.ChartFragActivity chartFragment = com.example.main.ChartFragActivity.newInstance("value1", "value2");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.chart_frame, chartFragment);
                    transaction.commit();
                }
            }
        });

        // playlist 버튼 클릭 시 색상 변경 및 레이아웃 변경
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

                    com.example.main.PlaylistFragActivity playlistFragment = com.example.main.PlaylistFragActivity.newInstance("value1", "value2");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.playlist_frame, playlistFragment);
                    transaction.commit();
                }
            }
        });

        // 마이페이지 버튼 클릭 이벤트 (마이페이지로 이동)
        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 마이 페이지 액티비티로 이동하는 Intent를 생성합니다.
                Intent mypage_intent = new Intent(MainActivity.this, MypageActivity.class);
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


            // SongAdapter 객체를 생성하고 ListView의 어댑터로 설정

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    // TODO : Chart 에서 노래 클릭 시 Study 탭으로 해당 노래를 추가할 수 있도록 하는 로직
                    Toast.makeText(requireContext(), "아이디가 존재않습니다.", Toast.LENGTH_SHORT);
                }
            });

            return ChartView;

        }

    }

    public static class PlaylistFragActivity extends Fragment {

        private ListView playlist_view; // STUDY 창의 프래그먼트로 연결된 리스트뷰

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // study 레이아웃 파일 내용이 main 레이아웃 파일의 프래그먼트 안으로
            View StudyView = inflater.inflate(R.layout.fragment_playlist_frag_activity, container, false);

            // 리스트뷰 아이디 부여
            playlist_view = StudyView.findViewById(R.id.playlist_view);

            String[][] data = {};

            List<String[]> playList = Arrays.asList(data);

            // PlaylistAdapter 객체를 생성하고 Listview의 어댑터로 설정
            PlaylistAdapter Padapter = new PlaylistAdapter(getActivity(), playList);
            playlist_view.setAdapter(Padapter);

            return StudyView;
        }

    }

}

