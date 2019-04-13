package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class ChatActivityManager {

    /**
     *  已读标记
     */
    public void readedSignAll(String userId){
        DBBase dbBase = DBBase.getDBBase();
        DaoSession dbSession = dbBase.getDBSession();
        MessageBeanDao messageBeanDao = dbSession.getMessageBeanDao();
        List<MessageBean> messageBeanQueryBuilder = messageBeanDao.queryBuilder().where(MessageBeanDao.Properties.SendUserId.eq(userId)).list();
        for (MessageBean messageBean:messageBeanQueryBuilder){
            messageBean.setReaded("1");
        }
        messageBeanDao.updateInTx(messageBeanQueryBuilder);
    }
}
