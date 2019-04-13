package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;

public class MessageBeanTest extends AbstractDaoTestLongPk<MessageBeanDao, MessageBean> {

    public MessageBeanTest() {
        super(MessageBeanDao.class);
    }

    @Override
    protected MessageBean createEntity(Long key) {
        MessageBean entity = new MessageBean();
        entity.setMessageId(key);
        entity.setMessageType();
        return entity;
    }

}
