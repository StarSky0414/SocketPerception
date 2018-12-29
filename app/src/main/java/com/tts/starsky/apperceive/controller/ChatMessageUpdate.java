package com.tts.starsky.apperceive.controller;

import android.util.Log;

import com.tts.starsky.apperceive.bean.ChatListBean;
import com.tts.starsky.apperceive.bean.evenbus.ChatMessageUpdateBean;
import com.tts.starsky.apperceive.db.provider.ChatContextDBProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ChatMessageUpdate {

    private final ChatContextDBProvider chatContextDBProvider;

    public ChatMessageUpdate(){
        EventBus.getDefault().register(this);
        chatContextDBProvider = new ChatContextDBProvider();
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void Event(ChatMessageUpdateBean chatMessageUpdateBean) {
        String userId = chatMessageUpdateBean.getUserId();
        ArrayList<ChatListBean> chatListBeans = chatContextDBProvider.querChatListBean(userId);
        EventBus.getDefault().post(chatListBeans);
        Log.w("=================","adapter.notifyDataSetChanged();");
    }
}
