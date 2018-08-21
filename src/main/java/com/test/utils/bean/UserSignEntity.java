package com.test.utils.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class UserSignEntity implements Serializable {

    @Excel(name = "名称")
    private String username;

    @Excel(name = "电话")
    private String phone;

    @Excel(name = "桌位号")
    private String tablename;

    @Excel(name = "备注")
    private String remark;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserSignEntity{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", tablename='" + tablename + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
