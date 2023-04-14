package com.example.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    // ChatGPT의 가이드 대로 대충 작성
    // 확인 후 수정 필요!

    SQLiteDatabase db;
    Context context;

    public DBManager(Context context)  {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public DBManager() {

    }

    public boolean checkEmailExist(String email) {
        // SQL 쿼리문을 작성합니다.
        String query = "SELECT * FROM user WHERE email = '" + email + "';";
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

    public boolean checkIdExist(String id) {
        // SQL 쿼리문을 작성합니다.
        String query = "SELECT * FROM user WHERE id = '" + id + "';";
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

    public void addUser(String email, String id, String username, String password) {

    };

        public SQLiteDatabase getWritableDatabase() {
            return null;
        }

    private class DBHelper {
        public DBHelper(Context context) {

        }

        public SQLiteDatabase getWritableDatabase() {
            return null;
        }
    }
}

