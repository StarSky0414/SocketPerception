package com.tts.starsky.apperceive.db.bean;

import com.tts.starsky.apperceive.R;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id(autoincrement = true)
    private Long id;
    private String nickname;
    private int headPhoto= R.mipmap.ic_launcher_round;
    private int unReadSign=1;
    @Generated(hash = 351890645)
    public UserBean(Long id, String nickname, int headPhoto, int unReadSign) {
        this.id = id;
        this.nickname = nickname;
        this.headPhoto = headPhoto;
        this.unReadSign = unReadSign;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
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

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", headPhoto=" + headPhoto +
                '}';
    }
    public int getUnReadSign() {
        return this.unReadSign;
    }
    public void setUnReadSign(int unReadSign) {
        this.unReadSign = unReadSign;
    }
}
