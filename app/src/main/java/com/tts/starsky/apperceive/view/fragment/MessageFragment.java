package com.tts.starsky.apperceive.view.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.RequestNewMessageList;
import com.tts.starsky.apperceive.bean.evenbus.UpdateMessageListBean;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnMessageSucc;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.controller.adapter.MessageListAdapter;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.provider.MessageListDBProvider;
import com.tts.starsky.apperceive.exception.DBException;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.manager.MessageFragmentManager;
import com.tts.starsky.apperceive.service.EvenBusEnumService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private List<MessageListBean> dataList;
    private MessageFragmentManager messageFragmentManager;
    private XRecyclerView mRecyclerView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    //    private MessageListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    @Subscribe
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 初始化数据
        MessageFragmentManager.DataInit();
        // 初始化控件
        messageFragmentManager = new MessageFragmentManager(this);

        View inflate = inflater.inflate(R.layout.fragment_chat_list, container, false);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerview);
        MessageListAdapter mAdapter = messageFragmentManager.getmAdapter();
        // XRecyclerView的使用，和RecyclerView几乎一致
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        // 可以设置是否开启加载更多/下拉刷新
        mRecyclerView.setLoadingMoreEnabled(true);
        // 可以设置加载更多的样式，很多种
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse);
        mRecyclerView.setBackgroundColor(Color.parseColor("#acacac"));
        // 添加刷新和加载更多的监听
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                String userId = UserStateInfo.getUserId();
                String userClientMessageId = UserStateInfo.getUserClientMessageId();
                SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userClientMessageId);
                System.out.println("syncTrendsBean: ============= " + syncMessageRequestBean.toString());
                LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
            }

            @Override
            public void onLoadMore() {
            }
        });
//        EventBus.getDefault().post(new RequestNewMessageList());
        return inflate;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        messageFragmentManager.updateMessageList();
    }


    public void updateViewFlush(){
        mRecyclerView.refreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
