package com.tts.starsky.apperceive.view.fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.TrendsListItemBean;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnTrendFlush;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;
import com.tts.starsky.apperceive.controller.adapter.TrendsListAdapter;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class TrendFragment extends Fragment {

    private TrendsListItemBean trendsListItemBean;

    public static TrendFragment newInstance() {
        TrendFragment trendFragment = new TrendFragment();
        return trendFragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        Log.d("============","=========");
//        return inflater.inflate(R.layout.fragment_trends, container, false);
//    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void update() {
        // XRecyclerView的使用，和RecyclerView几乎一致
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        // 可以设置是否开启加载更多/下拉刷新
        mRecyclerView.setLoadingMoreEnabled(true);

        // 可以设置加载更多的样式，很多种
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setBackgroundColor(Color.parseColor("#acacac"));

        // 添加刷新和加载更多的监听
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                mAdapter.addtData(testList());
//                mAdapter.changeData(dataList);
                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.refreshComplete();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                        Intent intentServer = new Intent(getContext(), MyService.class);
                        getContext().bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
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

                        myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, null);
                    }
                }, 2000);
            }
        });

    }

    // 定义
    private XRecyclerView mRecyclerView;
    private TrendsListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Intent intentServer = new Intent(getContext(), MyService.class);
//        getContext().bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
        EventBus.getDefault().register(this);

//        inflater.inflate(R.layout, container, false);

        Intent intentServer = new Intent(getContext(), MyService.class);
        getContext().bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
        View inflate = inflater.inflate(R.layout.fragment_trends, container, false);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.rl_recyclerview);

//        dataList = new MessageListDBProvider().queryMessageList();
//        MessageListBean messageListBean = new MessageListBean("1",1,"hahah","aa","2019-02-12 19:51:31",4);
        List<TrendsListItemBean> dataList = new ArrayList<>();
//        messageListBeans.add(messageListBean);
//        messageListBeans.add(messageListBean);
//        messageListBeans.add(messageListBean);

        dataList.add(trendsListItemBean);
        dataList.add(trendsListItemBean);
        dataList.add(trendsListItemBean);
        dataList.add(trendsListItemBean);
        mAdapter = new TrendsListAdapter(getContext(), dataList);

        update();
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private MyBinder myBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {



        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBinder) service;
            myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, null);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //SycnTrendFlush

    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendCreateCallBack(SycnTrendFlush sycnTrendFlush) {
        List<SendTrendsBean> sendTrendsBeanList = sycnTrendFlush.getSendTrendsBeanList();
        ArrayList<TrendsListItemBean> dataList = new ArrayList<>();
        for (SendTrendsBean sendTrendsBean : sendTrendsBeanList) {
            System.out.println("sendTrendsBeanList ========== : " + sendTrendsBean.toString());
            TrendsListItemBean trendsListItemBean = new TrendsListItemBean(sendTrendsBean.getSendUserId(), sendTrendsBean.getContent(), "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/" + sendTrendsBean.getUrl());
            dataList.add(trendsListItemBean);
        }
        mAdapter = new TrendsListAdapter(getContext(), dataList);
        update();
    }
}
