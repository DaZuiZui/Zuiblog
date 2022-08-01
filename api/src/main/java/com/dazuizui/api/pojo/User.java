package com.dazuizui.api.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class User implements Serializable
{
    //id
    @TableId(type = IdType.AUTO)
    private int id;
    //账号
    private String username;
    //密码
    private String password;
    //登入ip
    private String ip;
    //地区 or 行政区
    private String district;
    //Email
    private String email;
    //年
    private int year;
    //月
    private int month;
    //性别
    private String sex;
    //登入地区
    private String cstate;
    //头像url
    private String profilephotourl;
    //昵称
    private String name;
    //角色
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", district='" + district + '\'' +
                ", email='" + email + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", sex='" + sex + '\'' +
                ", cstate='" + cstate + '\'' +
                ", profilephotourl='" + profilephotourl + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCstate() {
        return cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String getProfilephotourl() {
        return profilephotourl;
    }

    public void setProfilephotourl(String profilephotourl) {
        this.profilephotourl = profilephotourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {
    }

    public User(int id, String username, String password, String ip, String district, String email, int year, int month, String sex, String cstate, String profilephotourl, String name, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.district = district;
        this.email = email;
        this.year = year;
        this.month = month;
        this.sex = sex;
        this.cstate = cstate;
        this.profilephotourl = profilephotourl;
        this.name = name;
        this.role = role;
    }
}
