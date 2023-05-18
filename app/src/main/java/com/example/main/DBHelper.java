package com.example.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {

    private static String db_Path = "";
    private static String db_Name = "userdata.db";
    private Context mContext;
    private SQLiteDatabase DataBase;

    private static String DB_TABLE = "song";
    private static String DB_TABLE2 = "wordbook";

    // Song columns name
    private static String col_id = "id";
    private static String col_rank = "rank";
    private static String col_name = "name";
    private static String col_singer = "singer";
    private static String col_lyrics = "lyrics";
    private static String col_like = "is_like";

    // wordbook columns name
    private static String col_mean = "mean";

    private static String col_lyrics_translation = "lyrics_translation";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        db_Path = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }

    private void dataBaseCheck() {
        File dbFile = new File(db_Path + db_Name);
        if (!dbFile.exists()) {
            dbCopy();
        }
    }
    @Override
    public synchronized void close() {
        if (DataBase != null) {
            DataBase.close();
        }
        super.close();
    }
    // db를 assets에서 복사해온다.
    private void dbCopy() {
        try {
            File folder = new File(db_Path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            InputStream inputStream = mContext.getAssets().open(db_Name);
            String out_filename = db_Path + db_Name;
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
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE if not exists USER(id CHAR(10), name CHAR(10), pwd CHAR(20),email CHAR(30))");
    }

    public void onCreateSong(SQLiteDatabase db) {
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public void onUpgradeSong(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP table if exists " + DB_TABLE);
    }

    public void Insert(SQLiteDatabase db ,String db_name ,String id, String name, String pwd, String email){
        db.execSQL("INSERT INTO '" + db_name + "' VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "')");
    }

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

    public void Update(SQLiteDatabase db,String db_name ,String id, String name, String pwd, String email){
        db.execSQL("UPDATE '" + db_name + "' SET id = '" + id + "',name = '" + name + "',pwd = '" + pwd + "',email = '" + email + "' WHERE id = '" + id + "'");
    }

    public boolean checkEmailExist(SQLiteDatabase db,String email) {
        // SQL 쿼리문을 작성합니다.
        String query = "SELECT * FROM USER WHERE email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);

        // 결과가 있으면 중복된 이메일이 있다는 뜻입니다.
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        // 결과가 없으면 중복된 이메일이 없다는 뜻입니다.
        cursor.close();
        return false;
    }

    public boolean checkIdExist(SQLiteDatabase db,String email) {
        // SQL 쿼리문을 작성합니다.
        String query = "SELECT * FROM USER WHERE email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);

        // 결과가 있으면 중복된 아이디가 있다는 뜻입니다.
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        // 결과가 없으면 중복된 아이디가 없다는 뜻입니다.
        cursor.close();
        return false;
    }



    public List<Playlist> getSongs2(){
        List<Playlist> playlists = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        // '1' 이라는 숫자가 좋아요입니다. 그래서 col_like 컬럼 값의 1인 것만, col_rank, col_name, col_singer을 결과를 가져온다.
        String query = " SELECT " + col_rank + ", " + col_name + ", " + col_singer +
                " FROM " + DB_TABLE + " Where " + col_like + "= 1" ;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int rank = cursor.getInt(0); // Index 1 is rank
                String name = cursor.getString(1); // Index 2 is name
                String singer = cursor.getString(2); // Index 3 is singer

                Playlist playlist = new Playlist(rank, name, singer);
                playlists.add(playlist);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return playlists;
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

    public void getUserData(SQLiteDatabase db, _USER user, String TABLE_NAME, String id)
    {
        String query ="SELECT * FROM '" +  TABLE_NAME + "' WHERE id = '" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!=null)
        {
            while(cursor.moveToNext()) {
                // _USER에 값 저장
                user.setUser_id(cursor.getString(0));
                user.setUser_name(cursor.getString(1));
                user.setUser_pwd(cursor.getString(2));
                user.setUser_email(cursor.getString(3));
            }
            cursor.close();
        }
    }

}
