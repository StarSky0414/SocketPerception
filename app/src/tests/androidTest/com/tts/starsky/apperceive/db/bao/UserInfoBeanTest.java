package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.tts.starsky.apperceive.db.bean.UserInfoBean;
import com.tts.starsky.apperceive.db.bao.UserInfoBeanDao;

public class UserInfoBeanTest extends AbstractDaoTestLongPk<UserInfoBeanDao, UserInfoBean> {

    public UserInfoBeanTest() {
        super(UserInfoBeanDao.class);
    }

    @Override
    protected UserInfoBean createEntity(Long key) {
        UserInfoBean entity = new UserInfoBean();
        entity.setId(key);
        entity.setSex();
        entity.setConstellation();
        entity.setState();
        return entity;
    }

}
