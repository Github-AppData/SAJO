package com.example.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "userdata.db";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE USER(id CHAR(10), name CHAR(10), pwd CHAR(20),email CHAR(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }

    public void Insert(String id, String name, String pwd, String email){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "'");
        db.close();
    }

    public void Update(String id, String name, String pwd, String email){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE INTO USER VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "'");
        db.close();
    }


    public boolean checkEmailExist(String email) {
        // SQL 쿼리문을 작성합니다.
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM USER WHERE email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);

        // 결과가 있으면 중복된 이메일이 있다는 뜻입니다.
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }

        // 결과가 없으면 중복된 이메일이 없다는 뜻입니다.
        cursor.close();
        return false;
    };

    public boolean checkIdExist(String email) {
        // SQL 쿼리문을 작성합니다.
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM USER WHERE email = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);

        // 결과가 있으면 중복된 이메일이 있다는 뜻입니다.
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }

        // 결과가 없으면 중복된 이메일이 없다는 뜻입니다.
        cursor.close();
        return false;
    };

}
