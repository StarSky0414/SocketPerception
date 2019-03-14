package com.tts.starsky.apperceive.controller.listonclicklistener;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.tts.starsky.apperceive.chaui.activity.ChatActivity;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.provider.MessageListDBProvider;

import java.util.List;

public class MessageListOnClickListener implements View.OnClickListener, View.OnLongClickListener {

    List<MessageListBean> dataList;
    int i ;
    public MessageListOnClickListener(List<MessageListBean> dataList, int i) {
        this.dataList=dataList;
        this.i=i;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "点击了一下", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(),ChatActivity.class);
        MessageListBean messageListBean = dataList.get(i);
        String userId = messageListBean.getUserId();
        String headPhoto = messageListBean.getHeadPhoto();
        String userNickName = messageListBean.getUserNickName();
        System.out.println("==============="+userId);

        MessageListDBProvider messageListDBProvider = new MessageListDBProvider();
        messageListDBProvider.updateUserUnreadedMessageNum(userId);

        intent.putExtra("userId",userId);
        intent.putExtra("headPhoto",headPhoto);
        intent.putExtra("userNickName",userNickName);
        v.getContext().startActivity(intent);

    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(v.getContext(), "长按了", Toast.LENGTH_SHORT).show();
        return true;
    }
}
