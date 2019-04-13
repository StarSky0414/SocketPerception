package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bao.UserBeanDao;

public class UserBeanTest extends AbstractDaoTestLongPk<UserBeanDao, UserBean> {

    public UserBeanTest() {
        super(UserBeanDao.class);
    }

    @Override
    protected UserBean createEntity(Long key) {
        UserBean entity = new UserBean();
        entity.setId(key);
        entity.setHeadPhoto();
        entity.setUnReadSign();
        return entity;
    }

}
