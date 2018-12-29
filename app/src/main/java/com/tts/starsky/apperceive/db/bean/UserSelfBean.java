package com.tts.starsky.apperceive.db.bean;

import com.tts.starsky.apperceive.R;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserSelfBean {

    @Id(autoincrement = true)
    private Long id;
    private String nickname;
    private int headPhoto= R.mipmap.ic_launcher_round;
    @Generated(hash = 1044392621)
    public UserSelfBean(Long id, String nickname, int headPhoto) {
        this.id = id;
        this.nickname = nickname;
        this.headPhoto = headPhoto;
    }
    @Generated(hash = 881718763)
    public UserSelfBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public int getHeadPhoto() {
        return this.headPhoto;
    }
    public void setHeadPhoto(int headPhoto) {
        this.headPhoto = headPhoto;
    }
}
