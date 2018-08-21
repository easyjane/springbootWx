package com.test.entity;

import java.util.Date;

public class UserSign {
    private String usid;

    private String username;

    private String phone;

    private String tableName;

    private Integer isSign;

    private Date createTime;

    private Date editTime;

    private Integer useable;

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid == null ? null : usid.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Integer getUseable() {
        return useable;
    }

    public void setUseable(Integer useable) {
        this.useable = useable;
    }

    public UserSign(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public UserSign() {
    }
}