package com.example.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public class DBSongHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "userdata.db";
    private static String DB_TABLE = "song";

    // columns name
    private static String col_id = "id";
    private static String col_rank = "rank";
    private static String col_name = "name";
    private static String col_singer = "singer";
    private static String col_lyrics = "lyrics";
    private static String col_lyrics_translation = "lyrics_translation";

    private static final String TAG = "DatabaseHelper";



    private Context context;

    // DataBase 객체
    private SQLiteDatabase db;

    public DBSongHelper(Context context) {
        super(context, DB_NAME, null, 1);
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        this.context = context;
        dataBaseCheck();
    }

    private void dataBaseCheck() {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            Log.d(TAG, "DB_PATH: " + DB_PATH);
            dbCopy();
        }
    }

    private void dbCopy() {
        try {
            File folder = new File(DB_PATH);
            if (!folder.exists()) {
                folder.mkdir();
            }
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String out_filename = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0) {
                outputStream.write(mBuffer,0,mLength);
            }
            outputStream.flush();;
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_TABLE + " ("+
                col_id + "integer primary key autoincrement not null, "+
                col_rank + "integer not null, "+
                col_name + "text not null, "+
                col_singer + "text not null,"+
                col_lyrics + "text not null,"+
                col_lyrics_translation + "text not null" + ") "
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP table if exists " + DB_TABLE);
    }

    // Data insert를 위한 메서드
    public boolean insertData(int id, int rank, String name, String singer, String lyrics, String lyrics_translation)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // contentValues 에다가 데이터 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_id, id);
        contentValues.put(col_rank, rank);
        contentValues.put(col_name, name);
        contentValues.put(col_singer, singer);
        contentValues.put(col_lyrics, lyrics);
        contentValues.put(col_lyrics_translation, lyrics_translation);

        // 저장한 데이터를 result 변수 에다가 옮긴다.
        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1; // if result = -1 data dosent insert
    }

    public List<Chart> getAllSongs() {
        List<Chart> songList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + col_rank + ", " + col_name + ", " + col_singer +
                " FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int rank = cursor.getInt(0); // Index 1 is rank
                String name = cursor.getString(1); // Index 2 is name
                String singer = cursor.getString(2); // Index 3 is singer

                Chart chart = new Chart(rank, name, singer);
                songList.add(chart);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return songList;
    }

}
