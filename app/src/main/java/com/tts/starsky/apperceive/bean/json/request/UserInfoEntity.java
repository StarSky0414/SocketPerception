package com.tts.starsky.apperceive.bean.json.request;


/**
 * 用户信息实体类
 */

public class UserInfoEntity {

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

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "id='" + id + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", passWord='" + passWord + '\'' +
                ", photoUser='" + photoUser + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", constellation='" + constellation + '\'' +
                '}';
    }
}
