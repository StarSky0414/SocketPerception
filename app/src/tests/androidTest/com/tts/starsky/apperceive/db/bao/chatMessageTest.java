package com.tts.starsky.apperceive.db.bao;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import com.tts.starsky.apperceive.db.bean.chatMessage;
import com.tts.starsky.apperceive.db.bao.chatMessageDao;

public class chatMessageTest extends AbstractDaoTestLongPk<chatMessageDao, chatMessage> {

    public chatMessageTest() {
        super(chatMessageDao.class);
    }

    @Override
    protected chatMessage createEntity(Long key) {
        chatMessage entity = new chatMessage();
        entity.setMessageId(key);
        entity.setUserId();
        entity.setReaded();
        return entity;
    }

}
