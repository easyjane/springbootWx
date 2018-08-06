package com.test.entity;

import java.util.Date;

public class UserFocus {
    private String ufid;

    private String uid;

    private Date createTime;

    private Integer type;

    public String getUfid() {
        return ufid;
    }

    public void setUfid(String ufid) {
        this.ufid = ufid == null ? null : ufid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserFocus(String ufid, String uid, Date createTime, Integer type) {
        this.ufid = ufid;
        this.uid = uid;
        this.createTime = createTime;
        this.type = type;
    }
}