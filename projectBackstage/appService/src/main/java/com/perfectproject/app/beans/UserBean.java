package com.perfectproject.app.beans;

import java.util.Date;

public class UserBean {

    private long id;
    private String phone;
    private String nick_name;
    private int sex;
    private int age;
    private long num_gold;
    private long num_rmb;
    private long num_Integral;
    private Date regist_time;
    private Date last_login_time;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getNum_gold() {
        return num_gold;
    }

    public void setNum_gold(long num_gold) {
        this.num_gold = num_gold;
    }

    public long getNum_rmb() {
        return num_rmb;
    }

    public void setNum_rmb(long num_rmb) {
        this.num_rmb = num_rmb;
    }

    public long getNum_Integral() {
        return num_Integral;
    }

    public void setNum_Integral(long num_Integral) {
        this.num_Integral = num_Integral;
    }

    public Date getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(Date regist_time) {
        this.regist_time = regist_time;
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }
}
