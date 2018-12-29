package com.tts.starsky.apperceive.bean.evenbus;

import com.tts.starsky.apperceive.db.bean.MessageListBean;

import java.util.List;


/**
 * 用于EvenBus消息传递
 */
public class UpdateMessageListBean {

    //消息列表
    private List<MessageListBean> messageListBeanList;

    public UpdateMessageListBean(List<MessageListBean> messageListBeanList) {
        this.messageListBeanList = messageListBeanList;
    }

    public List<MessageListBean> getMessageListBeanList() {
        return messageListBeanList;
    }
}
