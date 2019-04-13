package com.tts.starsky.apperceive.db.provider;

import android.database.Cursor;
import android.util.Log;

import com.tts.starsky.apperceive.chaui.bean.Message;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bao.UserBeanDao;
import com.tts.starsky.apperceive.db.bao.UserInfoBeanDao;
import com.tts.starsky.apperceive.db.bao.UserSelfBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bean.UserInfoBean;
import com.tts.starsky.apperceive.db.bean.UserSelfBean;
import com.tts.starsky.apperceive.exception.DBException;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListDBProvider {

    private DBBase dbBase;
    private DaoSession daoSession;

    /**
     * DB初始化
     */
//    static {
//
//    }

    public MessageListDBProvider(DaoSession daoSession) {
        this.daoSession=daoSession;
    }

    public MessageListDBProvider() {
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

//        String strSql = "group by other_user_id order by time DESC;";
        String strSqlCount = "select MESSAGE_CONTEXT ,TIME ,OTHER_USER_ID,MESSAGE_TYPE , count(readed) as unread_number from message_bean group by other_user_id order by time DESC;";

        ArrayList<MessageListBean> messageListBeanArrayList = new ArrayList<>();

        Cursor cursor = daoSession.getMessageBeanDao().getDatabase().rawQuery(strSqlCount, null);
        boolean b = cursor.moveToFirst();
        if (b) {
            do {
                MessageListBean messageListBean = new MessageListBean();
                int unread_number = cursor.getColumnIndex("unread_number");
                String cursorString = cursor.getString(unread_number);
                messageListBean.setUnreadMessageNumber(Integer.valueOf(cursorString));

                int message_type = cursor.getColumnIndex("MESSAGE_TYPE");
                int messageTypeString = cursor.getInt(message_type);
                messageListBean.setMessageType(messageTypeString);

                int other_user_id = cursor.getColumnIndex("OTHER_USER_ID");
                String otherUserIdString = cursor.getString(other_user_id);
                messageListBean.setUserId(otherUserIdString);

                int time = cursor.getColumnIndex("TIME");
                String timeString = cursor.getString(time);
                messageListBean.setTime(timeString);

                int message_context = cursor.getColumnIndex("MESSAGE_CONTEXT");
                String messageContextString = cursor.getString(message_context);
                messageListBean.setMessageContent(messageContextString);

                messageListBeanArrayList.add(messageListBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        UserInfoBeanDao userInfoBeanDao = daoSession.getUserInfoBeanDao();
        for (MessageListBean messageListBean : messageListBeanArrayList) {
            String otherUserId = messageListBean.getUserId();
            UserInfoBean userInfoBean = userInfoBeanDao.queryBuilder().where(UserInfoBeanDao.Properties.Id.eq(otherUserId)).unique();
            messageListBean.setHeadPhoto(userInfoBean.getPhotoUser());
            messageListBean.setUserNickName(userInfoBean.getNickName());
        }
        return messageListBeanArrayList;
    }

    public List<MessageBean> queryUserMessageByUserId(String userId, int maxNum) {
        List<MessageBean> list = daoSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.OtherUserId.eq(userId)).list();
        int size = list.size();
        List<MessageBean> messageBeanList = null;
        if (size > maxNum) {
            messageBeanList = list.subList(size - maxNum, size);
        } else {
            messageBeanList = list;
        }
        return messageBeanList;
    }


    public List<MessageBean> queryUpdateMessageByUserId(String userId) {
        List<MessageBean> list = daoSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.OtherUserId.eq(userId)).where(MessageBeanDao.Properties.Readed.eq(0)).list();
        return list;
    }


    /**
     * 更新用户未读消息数 将其更改为已读
     *
     * @param userId 用户id
     */
    public void updateUserUnreadedMessageNum(String userId) {
        String sqlString = "update message_bean set readed = null where other_user_id = ?; ";
        daoSession.getDatabase().execSQL(sqlString, new String[]{userId});
    }
}