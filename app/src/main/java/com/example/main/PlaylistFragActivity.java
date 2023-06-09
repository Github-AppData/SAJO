package com.example.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Use the {@link PlaylistFragActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaylistFragActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SQLiteDatabase db;
    DBHelper dbhelper;

    List<String> listItem = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlaylistFragActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudyFragActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaylistFragActivity newInstance(String param1, String param2) {
        PlaylistFragActivity fragment = new PlaylistFragActivity();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_playlist_frag_activity, container, false);

        ListView playListView = rootView.findViewById(R.id.playlist_view);

        dbhelper = new DBHelper(requireContext(), "userdata.db", null, 1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        // playlist.class, dbhelper.getAllSong2 만들어야 한다.
        ArrayList<Playlist> playList = dbhelper.getSongs2();

        if(playList.isEmpty()){
            Toast.makeText(requireContext(), "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Collections.sort(playList, new Comparator<Playlist>() {
                @Override
                public int compare(Playlist o1, Playlist o2) {
                    return Integer.compare(o1.getRank(), o2.getRank());
                }
            });

            ListAdapter adapter = new ArrayAdapter<Playlist>(requireContext(), R.layout.item_playlist, playList){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    if(convertView == null) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.item_playlist, parent, false);

                    }


                    TextView artistTextView = convertView.findViewById(R.id.playlist_song_artist);
                    TextView nameTextView = convertView.findViewById(R.id.playlist_song_name);


                    Playlist playlist = getItem(position);

                    if(playlist != null){
                        nameTextView.setText(""+playlist.getName());
                        artistTextView.setText(""+playlist.getSinger());
                    }

                    return convertView;
                }
            };
            playListView.setAdapter(adapter);

            playListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Study 액티비티로 이동하는 Intent를 생성합니다.
                    Intent study_intent = new Intent(parent.getContext(), StudyActivity.class);
                    study_intent.putExtra("position", position);
                    study_intent.putExtra("playlist", playList);
                    startActivity(study_intent); // 액티비티 실행
                }
            });
        }

        return rootView;
    }
}