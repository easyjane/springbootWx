package com.test.entity;

import java.util.Date;

public class User {
    private String uid;

    private String name;

    private String openId;

    private Date createTime;

    private String img;

    private Integer status;

    private String nickname;

    private Integer sex;

    private String country;

    private String province;

    private String city;

    private String language;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }


    public User(String uid, String name, String openId, Date createTime, String img, Integer status, String nickname, Integer sex, String country, String province, String city, String language) {
        this.uid = uid;
        this.name = name;
        this.openId = openId;
        this.createTime = createTime;
        this.img = img;
        this.status = status;
        this.nickname = nickname;
        this.sex = sex;
        this.country = country;
        this.province = province;
        this.city = city;
        this.language = language;
    }
}