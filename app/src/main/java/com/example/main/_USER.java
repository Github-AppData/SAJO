package com.example.main;

import java.util.List;

public class _USER {
    private static String user_id; // 사용자 id
    private static String user_name; // 사용자명
    private static String user_email; // 사용자 이메일
    private static String user_pwd; // 사용자 비밀번호


    public _USER(){
        this.user_id = "";
        this.user_name = "";
        this.user_email = "";
        this.user_pwd = "";
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }



}
