package com.tts.starsky.apperceive.service.callback;

import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.bean.json.request.MessageEntity;
import com.tts.starsky.apperceive.bean.json.request.MessageResponseBean;
import com.tts.starsky.apperceive.bean.json.request.MessgaeTypeEnum;
import com.tts.starsky.apperceive.bean.json.request.UserInfoEntity;
import com.tts.starsky.apperceive.chaui.bean.MsgType;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bao.UserInfoBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.UserInfoBean;
import com.tts.starsky.apperceive.exception.DBException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息同步
 * 服务器返回 回调
 */
public class SyncMessageNotice implements IMyCallBack {
    private DaoSession dbSession;

    /**
     * 消息回调
     *
     * @param requestJsonString 服务器返回的json
     */
    @Override
    public void callBack(String requestJsonString) {
        System.out.println("===================SyncMessageNotice: " + requestJsonString);
        MessageResponseBean messageResponseBean = JSONObject.parseObject(requestJsonString, MessageResponseBean.class);
        DBBase dbBase = null;
        try {
            dbBase = DBBase.getDBBase();
        } catch (DBException e) {
            e.printStackTrace();
        }

        List<MessageEntity> messageEntities = messageResponseBean.getMessageEntities();
        // 空数据不更新
        if (messageEntities == null || messageEntities.size() == 0) {
            return;
        }
        dbSession = dbBase.getDBSession();
        List<UserInfoEntity> messageUserInfos = messageResponseBean.getMessageUserInfo();
        List<UserInfoBean> userInfoBeans = messageUserInfoListToUserInfoBeanList(messageUserInfos);


        UserInfoBeanDao userInfoBeanDao = dbSession.getUserInfoBeanDao();
        userInfoBeanDao.insertOrReplaceInTx(userInfoBeans);


        List<MessageBean> messageBeanList = messageEntityListToMessageBeanList(messageEntities);

        MessageBeanDao messageBeanDao = dbSession.getMessageBeanDao();
        messageBeanDao.insertOrReplaceInTx(messageBeanList);

    }


    /**
     * 将（通讯）用户信息实体 转换 成（数据库）消息实体
     *
     * @param messageUserInfos （消息）用户实体
     * @return （数据库）消息实体
     */
    private List<UserInfoBean> messageUserInfoListToUserInfoBeanList(List<UserInfoEntity> messageUserInfos) {
        List<UserInfoBean> userInfoBeanArrayList = new ArrayList<UserInfoBean>();
        for (UserInfoEntity messageUserInfo : messageUserInfos) {
            String id = messageUserInfo.getId();
            int constellation = messageUserInfo.getConstellation();
            String nickName = messageUserInfo.getNickName();
            String photoUser = messageUserInfo.getPhotoUser();
            int sex = messageUserInfo.getSex();

            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setId(Long.valueOf(id));
            userInfoBean.setConstellation(constellation);
            userInfoBean.setNickName(nickName);
            userInfoBean.setPhotoUser(photoUser);
            userInfoBean.setSex(sex);

            userInfoBeanArrayList.add(userInfoBean);
        }
        return userInfoBeanArrayList;
    }

    /**
     * 将（通讯）消息实体  转换  成（数据库）消息实体
     * FOLLOW 类型是关系类型消息所以要更新用户信息数据库 updateUserFollow
     *
     * @param messageEntities（通讯）消息实体
     * @return （数据库）消息实体
     */
    private List<MessageBean> messageEntityListToMessageBeanList(List<MessageEntity> messageEntities) {

        List<MessageBean> messageBeanList = new ArrayList<MessageBean>();
        if (messageEntities.size()==0){
            return messageBeanList;
        }
        for (MessageEntity messageEntity : messageEntities) {
            MsgType messageType = messageEntity.getMessageType();
            System.out.println("===========MessgaeTypeEnum : " + messageType);
            if (messageType == MsgType.FOLLOW) {
                updateUserFollow(messageEntity);
                continue;
            }

            String messageid = messageEntity.getMessageid();
            int messageTypeNum = messageType.ordinal();
            String sendUserId = messageEntity.getSendUserId();
            String receiveUserId = messageEntity.getReceiveUserId();
            String createTime = messageEntity.getCreateTime();
            String messageContent = messageEntity.getMessageContent();

            MessageBean messageBean = new MessageBean();
            messageBean.setMessageId(Long.valueOf(messageid));
            messageBean.setMessageContext(messageContent);
            messageBean.setTime(createTime);
            messageBean.setSendUserId(sendUserId);
            messageBean.setReceiveUserId(receiveUserId);

            UserStateInfo userStateInfo = new UserStateInfo();
            String userId = userStateInfo.getUserId();
            if (sendUserId.equals(String.valueOf(userId))){
                messageBean.setOtherUserId(receiveUserId);
            }else {
                messageBean.setOtherUserId(sendUserId);

            }
            messageBean.setReaded("0"); //默认未读
            messageBean.setMessageType(messageTypeNum);

            messageBeanList.add(messageBean);

            Integer userClientMessageId = Integer.valueOf(userStateInfo.getUserClientMessageId());
            Long messageId = messageBean.getMessageId();
            if (userClientMessageId<messageId)
            {
                userStateInfo.setUserClientMessageId(String.valueOf(messageId));
            }
        }
        // 通知有新消息
        EventBus.getDefault().post(new MessageUpdateSign());
        return messageBeanList;
    }

    /**
     * 更新用户关系
     * 因为发送请求和被请求是有相对性，所以要根据发送和接收id进行转换，形成特定状态
     *
     * @param messageEntity 消息实体
     */
    private void updateUserFollow(MessageEntity messageEntity) {
        UserStateInfo userStateInfo = new UserStateInfo();
        String receiveUserId = messageEntity.getReceiveUserId();
        String userId;
        int ordinal = Integer.valueOf(messageEntity.getMessageContent());
        if (receiveUserId.equals(userStateInfo.getUserId())) {
            // 判断是被接受还是发送
            if (ordinal % 2 == 1) {
                ordinal -= 1;
            } else {
                ordinal += 1;
            }
            userId = messageEntity.getSendUserId();
        } else {
            userId = messageEntity.getReceiveUserId();
        }
        UserInfoBeanDao userInfoBeanDao = dbSession.getUserInfoBeanDao();
        UserInfoBean userInfoBean = userInfoBeanDao.queryBuilder().where(UserInfoBeanDao.Properties.Id.eq(userId)).unique();
        userInfoBean.setId(Long.valueOf(userId));
        userInfoBean.setState(ordinal);

        userInfoBeanDao.insertOrReplace(userInfoBean);
    }



}
