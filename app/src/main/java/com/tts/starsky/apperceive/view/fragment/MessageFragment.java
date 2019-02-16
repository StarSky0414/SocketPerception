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
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.evenbus.RequestNewMessageList;
import com.tts.starsky.apperceive.bean.evenbus.UpdateMessageListBean;
import com.tts.starsky.apperceive.controller.adapter.MessageListAdapter;
import com.tts.starsky.apperceive.db.bean.MessageListBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private List<MessageListBean> dataList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void update(){
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
//                mAdapter.addtData(testList());
                mAdapter.changeData(dataList);
                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.refreshComplete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mAdapter.addtData(null);
                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.loadMoreComplete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.loadMoreComplete();
                    }
                }, 2000);
            }
        });

    }

    // 定义
    private XRecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_chat_list, container, false);
        mRecyclerView=(XRecyclerView)inflate.findViewById(R.id.recyclerview);

//        dataList = new MessageListDBProvider().queryMessageList();
        MessageListBean messageListBean = new MessageListBean("1",1,"hahah","aa","2019-02-12 19:51:31",4);
        List<MessageListBean> messageListBeans = new ArrayList<>();
        messageListBeans.add(messageListBean);
        messageListBeans.add(messageListBean);
        messageListBeans.add(messageListBean);
        dataList = messageListBeans;
        mAdapter = new MessageListAdapter(getContext(), dataList);

        update();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post( new RequestNewMessageList());
        return inflate;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void Event(UpdateMessageListBean updateMessageListBean) {

        dataList = updateMessageListBean.getMessageListBeanList();
//        Toast.makeText(this, "aaaaa", Toast.LENGTH_SHORT).show();
//        update();
        mAdapter.changeData(dataList);
        Log.w("========","run Trends");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
