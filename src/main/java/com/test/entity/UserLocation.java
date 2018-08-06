package com.test.entity;

public class UserLocation {
    private String ulid;

    private String uid;

    private String openId;

    private String locationX;

    private String locationY;

    private String locationBdX;

    private String locationBdY;

    public String getUlid() {
        return ulid;
    }

    public void setUlid(String ulid) {
        this.ulid = ulid == null ? null : ulid.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX == null ? null : locationX.trim();
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY == null ? null : locationY.trim();
    }

    public String getLocationBdX() {
        return locationBdX;
    }

    public void setLocationBdX(String locationBdX) {
        this.locationBdX = locationBdX == null ? null : locationBdX.trim();
    }

    public String getLocationBdY() {
        return locationBdY;
    }

    public void setLocationBdY(String locationBdY) {
        this.locationBdY = locationBdY == null ? null : locationBdY.trim();
    }

    public UserLocation(String ulid, String uid, String openId, String locationX, String locationY, String locationBdX, String locationBdY) {
        this.ulid = ulid;
        this.uid = uid;
        this.openId = openId;
        this.locationX = locationX;
        this.locationY = locationY;
        this.locationBdX = locationBdX;
        this.locationBdY = locationBdY;
    }

    public UserLocation() {
    }
}