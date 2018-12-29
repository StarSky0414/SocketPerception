package com.tts.starsky.apperceive.db.provider;

import com.tts.starsky.apperceive.bean.ChatListBean;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.exception.DBException;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

public class ChatContextDBProvider {
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

    public ArrayList<ChatListBean> querChatListBean(String otherUserId){
        Property otherUserIdProperty = MessageBeanDao.Properties.OtherUserId;
        Property timeProperty = MessageBeanDao.Properties.Time;
        List<MessageBean> messageBeans = daoSession.getMessageBeanDao().queryBuilder().
                where(otherUserIdProperty.eq(otherUserId)).orderAsc(timeProperty).list();
        ArrayList<ChatListBean> chatListBeans = new ArrayList<>();
        for (MessageBean messageBean : messageBeans){
            System.out.println("==========="+messageBean.toString());
            ChatListBean.TYPE type= ChatListBean.TYPE.RECEIVED;
            if(messageBean.getReceiveUserId().equals(otherUserId)){
                type= ChatListBean.TYPE.SENT;
            }
            String messageContext = messageBean.getMessageContext();
            String time = messageBean.getTime();
            ChatListBean chatListBean = new ChatListBean(messageContext,type);
            chatListBeans.add(chatListBean);
        }
        return chatListBeans;
    }

    public void insertChatListBean(List<MessageBean> messageBeanList) throws DBException {
        long insert=0;
        MessageBeanDao messageBeanDao = daoSession.getMessageBeanDao();
        for(MessageBean messageBean:messageBeanList) {
             insert = messageBeanDao.insert(messageBean);
        }
        if (insert != messageBeanList.size()){
            throw new DBException(DBException.INSERTEXCEPTION);
        }
    }
}
