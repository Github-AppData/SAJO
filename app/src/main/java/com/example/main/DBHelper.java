package com.example.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class DBHelper extends SQLiteOpenHelper {

    private static String db_Path = "";
    private static String TAGG = "DBHelper";
    private static String db_Name = "userdata.db";
    private Context mContext;
    private SQLiteDatabase DataBase;

    private static String DB_TABLE = "song";
    private static String DB_TABLE2 = "wordbook";
    private static String DB_TABLE3 = "word_dictionary";

    // Song columns name
    private static String col_id = "id";
    private static String col_rank = "rank";
    private static String col_name = "name";
    private static String col_singer = "singer";
    private static String col_lyrics = "lyrics";
    private static String col_musicResId = "music_resid";
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

    public void onUpgradeWord(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DB_TABLE2 + " WHERE " + col_id + " = " + i);
    }

    public void Insert(SQLiteDatabase db ,String db_name ,String id, String name, String pwd, String email){
        db.execSQL("INSERT INTO '" + db_name + "' VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "')");
    }

    public boolean insertData(int id, int rank, String name, String singer, String lyrics, String lyrics_translation, String music_resid)
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

    // 이메일 체크
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

    // 아이디 체크 메서드
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

    // 플레이리스트 관련 메서드
    // userdata.db에서 쿼리를 수행한 뒤에, 쿼리의 결과를 반환하는 메서드입니다.
    public ArrayList<Playlist> getSongs2(){
        ArrayList<Playlist> playlists = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        // '1' 이라는 숫자가 좋아요입니다. 그래서 col_like 컬럼 값의 1인 것만, col_rank, col_name, col_singer을 결과를 가져온다.
        String query = " SELECT " + col_id + ", " + col_rank + ", " + col_name + ", " + col_singer + ", " + col_lyrics + ", " + col_musicResId +
                " FROM " + DB_TABLE + " Where " + col_like + " = 1" ;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0); // Index 1 is id
                int rank = cursor.getInt(1); // Index 2 is rank
                String name = cursor.getString(2); // Index 3 is name
                String singer = cursor.getString(3); // Index 4 is singer
                String lyrics = cursor.getString(4); // Index 5 is singer
                int musicResId = cursor.getInt(5); // Index 6 is singer

                Playlist playlist = new Playlist(id, rank, name, singer);
                playlist.setLyrics(lyrics);
                playlist.setMusicResId(musicResId);
                playlists.add(playlist);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return playlists;
    }

    // 단어장 관련 메서드
    // 설명은 170번째 줄 하고 동일
    // TODO : 단어 검색창에서 즐겨찾기 버튼을 누르면, wordbook list에 들어가는 기능 추가 해야 됩니다.
    public List<Wordbook> getWord(){
        List<Wordbook> wordbook = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        // '1' 이라는 숫자가 좋아요입니다. - col_rank, col_name, col_singer을 결과를 가져온다.
        String query = " SELECT " + col_name + ", " + col_mean +
                " FROM " + DB_TABLE2;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0); // Index 1 is name
                String mean = cursor.getString(1); // Index 2 is mean

                Wordbook wordbook1 = new Wordbook (name, mean);
                wordbook.add(wordbook1);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return wordbook;
    }

    // 단어 사전에서 해당 단어에 대한 의미를 찾는 메서드
    public String getWordMean(String word){
        String mean = "현재 단어사전에 등록되어 있지 않은 단어입니다.";
        SQLiteDatabase db = this.getWritableDatabase();

        // '1' 이라는 숫자가 좋아요입니다. 그래서 col_like 컬럼 값의 1인 것만, col_rank, col_name, col_singer을 결과를 가져온다.
        String query = " SELECT " + col_mean +
                " FROM " + DB_TABLE3 + " Where " + col_name + " = '" + word + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext()) {
            mean = cursor.getString(0);

            cursor.close();
            db.close();

            return mean;
        }
        return mean;
    }

    // 메모 버튼 클릭 시 검색한 단어가 나의 단어장에 추가하고 성공을 출력하는 메서드
    // (단, '현재 단어사전에 등록되어 있지 않은 단어입니다.'일 경우 불가합니다. '불가' 출력)
    public String addMyWord(String mean){
        Log.v("mean : ", mean);
        if(mean.equals("현재 단어사전에 등록되어 있지 않은 단어입니다.")){
            return "사전에 등록되지 않은 단어는 단어장에 추가할 수 없습니다.";
        }
        if(mean.equals("단어 검색 결과")){
            return "단어장에 추가할 단어를 검색해주세요.";
        }

        String word = "";
        SQLiteDatabase db = this.getWritableDatabase();

        // 영단어 사전에서 name 값을 가져오기
        String query = " SELECT " + col_name +
                " FROM word_dictionary Where " + col_mean + " = '" + mean + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            word = cursor.getString(0);
        }

        // 단어장에 검색한 단어가 이미 있는지 확인
        query = " SELECT " + col_name +
                " FROM wordbook Where " + col_mean + " = '" + mean + "'";
        cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            cursor.close();
            return "검색하신 단어는 이미 단어장에 있는 단어입니다.";
        }

        // 나의 단어장에 검색한 단어와 의미 추가
        db.execSQL("INSERT INTO wordbook(name, mean) VALUES('" + word + "','" + mean + "')");

        return "단어와 뜻이 나의 단어장에 추가되었습니다.";
    }

    // 차트 관련 메서드 
    // 설명은 170번째 줄 설명하고 비슷함.
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
