package com.example.main;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordbookFragActivity#newInstance} factory method to
 * create an instance of this fragment.
 */



public class WordbookFragActivity extends Fragment {
    private static final String TAG = "WordbookFragActivity";

    SQLiteDatabase db;
    DBHelper dbhelper;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static WordbookFragActivity newInstance(String param1, String param2) {
        WordbookFragActivity fragment = new WordbookFragActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wordbook_frag_activity, container, false);

        ListView wordListView = rootView.findViewById(R.id.wordbook_list);

        dbhelper = new DBHelper(requireContext(), "userdata.db", null, 1);
        db = dbhelper.getWritableDatabase();
        dbhelper.onCreate(db);
        db.close();

        List<Wordbook> wordbooks = dbhelper.getWord();

        if(wordbooks.isEmpty()){
            Log.d(TAG, "데이터가 없습니다.");
            Toast.makeText(requireContext(),"데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }



        ArrayAdapter adapter = new ArrayAdapter<Wordbook>(requireContext(), R.layout.activity_wordbook, wordbooks) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.activity_wordbook, parent, false);
                }

                TextView artistTextView = convertView.findViewById(R.id.word);
                TextView nameTextView = convertView.findViewById(R.id.word_mean);

                Wordbook wordbook = getItem(position);

                nameTextView.setText("" + wordbook.getName());
                artistTextView.setText("" + wordbook.getMean());

                return convertView;
            }
        };

        wordListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("remove Word : ", wordbooks.get(i).getName());
                // dbhelper.onUpgradeWord(i+1);
                wordbooks.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        wordListView.setAdapter(adapter);

        return rootView;
    }
}

