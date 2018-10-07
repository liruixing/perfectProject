package com.perfectproject.app.beans;

import java.util.Date;

public class RequirementBean {
    private long id;
    private long user_id;
    private int type;
    private int game_type;
    private long unit_price;
    private int count;
    private int sex;
    private Date appointment_time;
    private String info;
    private int status;
    private Date create_time;
    private Date update_time;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public long getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(long unit_price) {
        this.unit_price = unit_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(Date appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
