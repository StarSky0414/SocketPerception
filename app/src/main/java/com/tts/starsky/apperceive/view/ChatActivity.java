package com.tts.starsky.apperceive.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.ChatListBean;
import com.tts.starsky.apperceive.bean.service.SendMessageBean;
import com.tts.starsky.apperceive.controller.ChatMessageUpdate;
import com.tts.starsky.apperceive.bean.evenbus.ChatMessageUpdateBean;
import com.tts.starsky.apperceive.controller.adapter.ChatListAdapter;
import com.tts.starsky.apperceive.db.provider.ChatContextDBProvider;
import com.tts.starsky.apperceive.db.provider.UserStateDBProvider;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {


    private List<ChatListBean> ChatListBeanList = new ArrayList<>();
    private ChatContextDBProvider chatContextDBProvider;
    private ChatListAdapter adapter;
    private RecyclerView chatListBeanRecyclerView;
    private String SendContent;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MyBinder service1 = (MyBinder) service;
            System.out.println("========== onServiceConnected");
            UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
            String userId = userStateDBProvider.queryUserState().getUserId();
            SendMessageBean sendMessageBean = new SendMessageBean(userId,"1",SendContent,null);
            service1.adapterExceptionDispose(EvenBusEnumService.SEND_MESSAGE,sendMessageBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_chat);
//        chatContextDBProvider = new ChatContextDBProvider();
//        EventBus.getDefault().register(this);
//        init();



        final EditText inputText=(EditText)findViewById(R.id.input);
        Button sendBtn=(Button)findViewById(R.id.send);


//        chatListBeanRecyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=inputText.getText().toString();
                if("".equals(content))
                    return;
                SendContent=content;
                ChatListBeanList.add(new ChatListBean(content, ChatListBean.TYPE.SENT));

                //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                int newSize = ChatListBeanList.size() - 1;
                adapter.notifyItemInserted(newSize);
                chatListBeanRecyclerView.scrollToPosition(newSize);
//                SendMessageBean sendMessageBean = null;
//                try {
//                    sendMessageBean = new SendMessageBean("2","1",new String(content.getBytes(),"UTF-8"),null);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(ChatActivity.this, MyService.class);
                bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
                //清空输入框中的内容
                inputText.setText("");


            }
        });

    }

    /**
     * 初始化消息数据
     */
    private void init() {
        ChatListBeanList.add(new ChatListBean("你好", ChatListBean.TYPE.RECEIVED));
        ChatListBeanList.add(new ChatListBean("你好，请问你是？", ChatListBean.TYPE.SENT));
        ChatListBeanList.add(new ChatListBean("我是 deniro，很高兴认识你^_^", ChatListBean.TYPE.RECEIVED));
        Intent intent = getIntent();//获取传来的intent对象
        String userId = intent.getStringExtra("userId");//获取键值对的键名
        System.out.println("===============userId: "+userId);
//        ArrayList<ChatListBean> chatListBeans = chatContextDBProvider.querChatListBean(userId);
//        ChatListBeanList.addAll(chatListBeans);
//        ChatListBeanList=chatListBeans;
        ChatMessageUpdate chatMessageUpdate = new ChatMessageUpdate();
        ChatMessageUpdateBean chatMessageUpdateBean = new ChatMessageUpdateBean(userId);
//        chatMessageUpdate.updateChatList(userId);
        EventBus.getDefault().post(chatMessageUpdateBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void Event(ArrayList<ChatListBean> chatListBeanList) {
        ChatListBeanList=chatListBeanList;
        int newSize = ChatListBeanList.size() - 1;
//        adapter.notifyItemInserted(newSize);
        chatListBeanRecyclerView = (RecyclerView)findViewById(R.id.msg);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatListBeanRecyclerView.setLayoutManager(layoutManager);

        adapter = new ChatListAdapter(ChatListBeanList);
//        adapter.notifyDataSetChanged();
        chatListBeanRecyclerView.setAdapter(adapter);
        chatListBeanRecyclerView.scrollToPosition(newSize);
        Log.w("=================","adapter.notifyDataSetChanged();");
    }
}
