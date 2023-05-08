package com.example.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DBHelper extends SQLiteOpenHelper {

    private static String db_Path = "";
    private static String db_Name = "userdata.db";
    private Context mContext;
    private SQLiteDatabase DataBase;


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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public void Insert(SQLiteDatabase db ,String db_name ,String id, String name, String pwd, String email){
        db.execSQL("INSERT INTO '" + db_name + "' VALUES('" + id + "','" + name + "','" + pwd + "', '" + email + "')");
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

        // 결과가 있으면 중복된 이메일이 있다는 뜻입니다.
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        // 결과가 없으면 중복된 이메일이 없다는 뜻입니다.
        cursor.close();
        return false;
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
