package com.tts.starsky.apperceive.service.callback;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.ChatListBean;
import com.tts.starsky.apperceive.bean.service.UserChatMessage;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.UserBeanDao;
import com.tts.starsky.apperceive.db.bao.UserSelfBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bean.UserSelfBean;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.db.provider.ChatContextDBProvider;
import com.tts.starsky.apperceive.db.provider.MySelfDBProvider;
import com.tts.starsky.apperceive.db.provider.UserStateDBProvider;
import com.tts.starsky.apperceive.exception.DBException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class newMessageNotice implements IMyCallBack {

    @Override
    public void callBack(String requestJsonString) {
        System.out.println("=============="+requestJsonString);
        List<UserChatMessage> messageBeanList = JSON.parseArray(requestJsonString, UserChatMessage.class);
        for (UserChatMessage messageBean : messageBeanList){
            System.out.println("=================="+messageBean.toString());
        }


        testInsert();

        System.out.println("==================== callBack");
        List<MessageBean> messageBeanList1 = userChatMessageToMessageBean(messageBeanList);
        ChatContextDBProvider chatContextDBProvider = new ChatContextDBProvider();

        if (messageBeanList1.size()==0) {
            return;
        }
        Long messageId = messageBeanList1.get(messageBeanList1.size()-1).getMessageId();
        System.out.println("=============messageId: "+messageId);

        testInsertUserState();

        UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
        UserStateBean userStateBean = userStateDBProvider.queryUserState();
        String userLastMessageId = userStateBean.getUserLastMessageId();
        userStateBean.setUserLastMessageId(String.valueOf(messageId));
        System.out.println("==================userLastMessageId: "+userLastMessageId);

        try {
            chatContextDBProvider.insertChatListBean(messageBeanList1);
        } catch (DBException e) {
            e.printStackTrace();
        }
//        test();
//        testInsert();
        updateUserState(userStateBean);
    }

    private List<MessageBean> userChatMessageToMessageBean(List<UserChatMessage>  userChatMessages){
        MySelfDBProvider mySelfDBProvider = new MySelfDBProvider();
        UserSelfBean userSelfBean = mySelfDBProvider.queryMySelfInfo();
        List<MessageBean> messageBeanList = new ArrayList<MessageBean>();
        for (UserChatMessage chatMessage : userChatMessages){
            int id = chatMessage.getId();
            String send_user_id = chatMessage.getSend_user_id();
            String receive_user_id = chatMessage.getReceive_user_id();
            String message_content = chatMessage.getMessage_content();
            String type = chatMessage.getType();
            Timestamp time = chatMessage.getTime();
            long voiceTime = chatMessage.getVoiceTime();

            MessageBean messageBean = new MessageBean();
            messageBean.setMessageId((long)id);
            messageBean.setSendUserId(send_user_id);
            messageBean.setReceiveUserId(receive_user_id);
            messageBean.setMessageContext(message_content);
            messageBean.setTime(String.valueOf(time));


            if (send_user_id.equals(String.valueOf(userSelfBean.getId()))){
                messageBean.setOtherUserId(receive_user_id);
            }else {
                messageBean.setOtherUserId(send_user_id);
            }
            messageBeanList.add(messageBean);
        }
        return messageBeanList;
    }

    private void test(){
        ArrayList<ChatListBean> chatListBeans = new ChatContextDBProvider().querChatListBean("1");
        for (ChatListBean chatListBean : chatListBeans){
            System.out.println("====================="+chatListBean.toString());
        }

        System.out.println("=========test is over");
    }

    private void testInsert(){
        DaoSession daoSession = null;
        try {
            daoSession  = DBBase.getDBBase().getDBSession();
        } catch (DBException e) {
            e.printStackTrace();
        }
        UserSelfBeanDao userSelfBeanDao = daoSession.getUserSelfBeanDao();
        userSelfBeanDao.insert(new UserSelfBean(2L, "aaaa", R.mipmap.ic_launcher_round));

        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
        userBeanDao.insert(new UserBean(Long.valueOf(1), "这是大哥", R.mipmap.ic_launcher_round, 0));
        userBeanDao.insert(new UserBean(Long.valueOf(3), "这是大哥2", R.mipmap.ic_launcher_round, 0));
    }

    private void updateUserState(UserStateBean userStateBean){
        UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
        userStateDBProvider.saveUserState(userStateBean);
    }

    private void testInsertUserState(){
        UserStateBean userStateBean = new UserStateBean("2", "哈哈哈", "男", "347876c4105f498cb6ec9547d198fe5e", "5");
        UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
        userStateDBProvider.saveUserState(userStateBean);
    }
}
