package com.tts.starsky.apperceive.db.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户信息实体类
 */

@Entity
public class UserInfoBean {

    @Id
    // 用户id
    private Long id;

    // 用户头像
    private String photoUser;

    // 用户昵称
    private String nickName;

    // 用户性别
    private int  sex;

    // 用户星座
    private int constellation;

    // 用户关系
    private int state;

    @Generated(hash = 1631498893)
    public UserInfoBean(Long id, String photoUser, String nickName, int sex,
            int constellation, int state) {
        this.id = id;
        this.photoUser = photoUser;
        this.nickName = nickName;
        this.sex = sex;
        this.constellation = constellation;
        this.state = state;
    }

    @Generated(hash = 1818808915)
    public UserInfoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUser() {
        return this.photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getConstellation() {
        return this.constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

  
}
