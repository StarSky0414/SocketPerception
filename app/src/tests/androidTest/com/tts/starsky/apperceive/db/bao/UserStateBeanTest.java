package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestStringPk;

import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.db.bao.UserStateBeanDao;

public class UserStateBeanTest extends AbstractDaoTestStringPk<UserStateBeanDao, UserStateBean> {

    public UserStateBeanTest() {
        super(UserStateBeanDao.class);
    }

    @Override
    protected UserStateBean createEntity(String key) {
        UserStateBean entity = new UserStateBean();
        entity.setUserId(key);
        return entity;
    }

}
