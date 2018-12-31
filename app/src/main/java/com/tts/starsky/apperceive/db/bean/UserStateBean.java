package com.tts.starsky.apperceive.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *  用户状态信息
 */

@Entity
public class UserStateBean


{
    // 用户ID
    @Id
    private String userId;
    // 用户昵称
    private String userNickName;
    // 用户性别
    private String userSex;
    // 用户session
    private String userSession;
    // 用户消息最后id
    private String userLastMessageId;

    @Generated(hash = 453617545)
    public UserStateBean(String userId, String userNickName, String userSex,
            String userSession, String userLastMessageId) {
        this.userId = userId;
        this.userNickName = userNickName;
        this.userSex = userSex;
        this.userSession = userSession;
        this.userLastMessageId = userLastMessageId;
    }

    @Generated(hash = 985931012)
    public UserStateBean() {
    }

    @Override
    public String toString() {
        return "UserState{" +
                "userId='" + userId + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userSession='" + userSession + '\'' +
                ", userLastMessageId='" + userLastMessageId + '\'' +
                '}';
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserSex() {
        return this.userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSession() {
        return this.userSession;
    }

    public void setUserSession(String userSession) {
        this.userSession = userSession;
    }

    public String getUserLastMessageId() {
        return this.userLastMessageId;
    }

    public void setUserLastMessageId(String userLastMessageId) {
        this.userLastMessageId = userLastMessageId;
    }
}
