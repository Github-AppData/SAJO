package com.example.main;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChartFragActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragActivity extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SQLiteDatabase db;
    DBHelper dbhelper;

    List<String> listItem = new ArrayList<>();


    private String mParam1;
    private String mParam2;

    public ChartFragActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragActivity.
     */

    public static ChartFragActivity newInstance(String param1, String param2) {
        ChartFragActivity fragment = new ChartFragActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    
    // 데이터를 가져와 리스트뷰에 표시하는 메서드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart_frag_activity, container, false);

        ListView songListView = rootView.findViewById(R.id.song_list);

        dbhelper = new DBHelper(requireContext(), "userdata.db", null, 1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        List<Chart> songList = dbhelper.getAllSongs();

        if (songList.isEmpty()) {
            Toast.makeText(requireContext(), "No", Toast.LENGTH_SHORT).show();
        } else {
            Collections.sort(songList, new Comparator<Chart>() {
                @Override
                public int compare(Chart chart1, Chart chart2) {
                    return Integer.compare(chart1.getRank(), chart2.getRank());
                }
            });

            ListAdapter adapter = new ArrayAdapter<Chart>(requireContext(), R.layout.item_list, songList) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.item_list, parent, false);
                    }

                    TextView rankTextView = convertView.findViewById(R.id.song_rank);
                    TextView titleTextView = convertView.findViewById(R.id.song_title);
                    TextView artistTextView = convertView.findViewById(R.id.song_artist);

                    Chart chart = getItem(position);
                    if (chart != null) {
                        rankTextView.setText("" + chart.getRank());
                        titleTextView.setText("" + chart.getName());
                        artistTextView.setText("" + chart.getSinger());
                    }

                    return convertView;
                }
            };

            songListView.setAdapter(adapter);
        }

        return rootView;
    }

}