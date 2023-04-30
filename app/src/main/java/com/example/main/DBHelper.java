package com.example.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE if not exists USER(id CHAR(10), name CHAR(10), pwd CHAR(20),email CHAR(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public void Insert(SQLiteDatabase db,String id, String name, String pwd, String email){
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO USER VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "'");
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void Update(SQLiteDatabase db,String id, String name, String pwd, String email){
        db.execSQL("UPDATE INTO USER VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "'");
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

        // 결과가 있으면 중복된 이메일이 있다는 뜻입니다.
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        // 결과가 없으면 중복된 이메일이 없다는 뜻입니다.
        cursor.close();
        return false;
    }

}
