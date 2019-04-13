package com.tts.starsky.apperceive.chaui.bean;

import java.util.List;

/**
 * 其他用户详情查看
 */
public class OtherUserInfoQuery {
    // 用户id
    private String id;

    // 用户账户（电话）
    private String userPhone;

    // 用户密码
    private String passWord;

    // 用户头像
    private String photoUser;

    // 用户昵称
    private String nickName;

    // 用户性别
    private int  sex;

    // 用户星座
    private int constellation;

    // 用户动态图片
    private List<String> userTrendsPhoto;

    public OtherUserInfoQuery() {
    }

    public OtherUserInfoQuery(String id, String userPhone, String passWord, String photoUser, String nickName, int sex, int constellation, List<String> userTrendsPhoto) {
        this.id = id;
        this.userPhone = userPhone;
        this.passWord = passWord;
        this.photoUser = photoUser;
        this.nickName = nickName;
        this.sex = sex;
        this.constellation = constellation;
        this.userTrendsPhoto = userTrendsPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getConstellation() {
        return constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public List<String> getUserTrendsPhoto() {
        return userTrendsPhoto;
    }

    public void setUserTrendsPhoto(List<String> userTrendsPhoto) {
        this.userTrendsPhoto = userTrendsPhoto;
    }

    @Override
    public String toString() {
        return "OtherUserInfoQuery{" +
                "id='" + id + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", passWord='" + passWord + '\'' +
                ", photoUser='" + photoUser + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", constellation=" + constellation +
                ", userTrendsPhoto=" + userTrendsPhoto +
                '}';
    }
}
