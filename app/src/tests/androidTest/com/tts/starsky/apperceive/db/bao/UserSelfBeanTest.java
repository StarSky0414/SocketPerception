package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.tts.starsky.apperceive.db.bean.UserSelfBean;
import com.tts.starsky.apperceive.db.bao.UserSelfBeanDao;

public class UserSelfBeanTest extends AbstractDaoTestLongPk<UserSelfBeanDao, UserSelfBean> {

    public UserSelfBeanTest() {
        super(UserSelfBeanDao.class);
    }

    @Override
    protected UserSelfBean createEntity(Long key) {
        UserSelfBean entity = new UserSelfBean();
        entity.setId(key);
        entity.setHeadPhoto();
        return entity;
    }

}
