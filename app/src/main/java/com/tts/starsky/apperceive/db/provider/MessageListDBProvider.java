package com.tts.starsky.apperceive.db.provider;

import android.util.Log;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bao.UserBeanDao;
import com.tts.starsky.apperceive.db.bao.UserSelfBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bean.UserSelfBean;
import com.tts.starsky.apperceive.exception.DBException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListDBProvider {

    private static DBBase dbBase;
    private static DaoSession daoSession;

    /**
     * DB初始化
     */
    static {
        try {
            dbBase = DBBase.getDBBase();
        } catch (DBException e) {
            e.printStackTrace();
        }
        daoSession = dbBase.getDBSession();
    }
//
//    /**
//     * 插入数据库，消息同步
//     *
//     * @param messageBeanList 消息list
//     */
//    public void insertDB(List<MessageBean> messageBeanList) {
//        if (daoSession != null) {
//            MessageBeanDao messageBeanDao = daoSession.getMessageBeanDao();
//            for (MessageBean messageBean : messageBeanList) {
//                messageBeanDao.insert(messageBean);
//            }
//        } else {
//            Log.e(DBException.TAG, DBException.UNINITDAOSESSIONEXCEPTION);
//        }
//    }

    /**
     * 查询消息列表
     *
     * @return 消息列表list
     */
    public List<MessageListBean> queryMessageList() {
        ArrayList<MessageListBean> messageListBeans = new ArrayList<>();

        if (daoSession == null) {
            Log.e(DBException.TAG, DBException.UNINITDAOSESSIONEXCEPTION);
            return messageListBeans;
        }


        UserSelfBeanDao userSelfBeanDao = daoSession.getUserSelfBeanDao();
        UserSelfBean unique = userSelfBeanDao.queryBuilder().unique();
        if (unique == null) {
            return messageListBeans;
        }

        List<UserBean> userBeanQueryBuilder = daoSession.getUserBeanDao().queryBuilder().where(UserBeanDao.Properties.UnReadSign.eq(0)).list();
        MessageBeanDao messageBeanDao = daoSession.getMessageBeanDao();
        for ( UserBean otherUser :userBeanQueryBuilder ){
            Long id = otherUser.getId();
            List<MessageBean> messageBeanList = messageBeanDao.queryBuilder().where(MessageBeanDao.Properties.OtherUserId.eq(id))
                    .orderDesc(MessageBeanDao.Properties.Time).list();
            int unReadMessageNumber = messageBeanList.size();
            if (unReadMessageNumber != 0) {
                MessageBean messageBean=messageBeanList.get(0);
                MessageListBean messageListBean = new MessageListBean(
                        String.valueOf(otherUser.getId()),
                        otherUser.getHeadPhoto(),
                        otherUser.getNickname(),
                        messageBean.getMessageContext(),
                        messageBean.getTime(),
                        unReadMessageNumber);
                messageListBeans.add(messageListBean);
            }

        }
        return messageListBeans;
    }
}