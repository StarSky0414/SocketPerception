package com.tts.starsky.apperceive.view.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tts.starsky.apperceive.*;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tts.starsky.apperceive.bean.TrendsListItemBean;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnTrendFlush;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.controller.adapter.TrendsListAdapter;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;
import com.tts.starsky.apperceive.view.SendTrendActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class TrendFragment extends Fragment implements View.OnClickListener {

    // 定义
    private XRecyclerView mRecyclerView;
    private TrendsListAdapter mAdapter;


    public static TrendFragment newInstance() {
        TrendFragment trendFragment = new TrendFragment();
        return trendFragment;
    }

    /**
     * 初始化RecyclerView
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void init(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);



        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        // 可以设置是否开启加载更多/下拉刷新
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setPullRefreshEnabled(true);

        // 可以设置加载更多的样式，很多种
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallGridPulse);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        // 背景样式
        mRecyclerView.setBackgroundColor(Color.parseColor("#acacac"));
        // 添加刷新和加载更多的监听
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            // 下拉刷新
            @Override
            public void onRefresh() {
//                mAdapter.addtData(testList());
//                mAdapter.changeData(dataList);

                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.refreshComplete();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                }, 2000);
                SyncTrendsBean syncTrendsBean = new SyncTrendsBean("",null);
                myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);

            }

            @Override
            public void onLoadMore() {
                UserStateInfo userStateInfo = new UserStateInfo();
                SyncTrendsBean syncTrendsBean = new SyncTrendsBean(userStateInfo.getUserId(), String.valueOf(mAdapter.getMinId()));
                myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_LOAD, syncTrendsBean);

                // 为了看效果，加了一个等待效果，正式的时候直接写mRecyclerView.loadMoreComplete();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        Intent intentServer = new Intent(getContext(), MyService.class);
        getContext().bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);



        View inflate = inflater.inflate(R.layout.fragment_trends, container, false);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.rl_recyclerview);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mRecyclerView.getLayoutParams());
        layoutParams.setMargins(0,0,0,150);
        mRecyclerView.setLayoutParams(layoutParams);

        ImageView tv_top_trend_right = (ImageView) inflate.findViewById(R.id.tv_top_trend_right);
        tv_top_trend_right.setOnClickListener(this);



        List<TrendsListItemBean> dataList = new ArrayList<>();
        mAdapter = new TrendsListAdapter(getContext(), dataList);

        init();
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     *   服务调用
     */
    private MyBinder myBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBinder) service;
            mAdapter.setHideOtherUser();
            SyncTrendsBean syncTrendsBean = new SyncTrendsBean("",null);
            myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     *  数据请求回调
     * @param sycnTrendFlush 服务器返回数据
     */

    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendCreateCallBack(SycnTrendFlush sycnTrendFlush) {
        List<SendTrendsBean> sendTrendsBeanList = sycnTrendFlush.getSendTrendsBeanList();
        ArrayList<TrendsListItemBean> dataList = new ArrayList<>();
        for (SendTrendsBean sendTrendsBean : sendTrendsBeanList) {
            System.out.println("sendTrendsBeanList ========== : " + sendTrendsBean.toString());
            TrendsListItemBean trendsListItemBean = new TrendsListItemBean(Integer.valueOf(sendTrendsBean.getTrendId()),sendTrendsBean.getSendUserId(), sendTrendsBean.getTrendContent(), "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/" + sendTrendsBean.getTrendPhotoUrl());
            dataList.add(trendsListItemBean);
        }

        SycnTrendFlush.StatSign statSign = sycnTrendFlush.getStatSign();
        switch (statSign){
            case Load:
                mAdapter.addtData(dataList);
                System.out.println("Load ============");
                break;
            case Flush:
                mAdapter.changeData(dataList);
                System.out.println("Flush ============");
                break;
        }
        mRecyclerView.refreshComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_top_trend_right:
                Intent intent=new Intent(this.getActivity(), SendTrendActivity.class);
                startActivity(intent);
        }
    }
}
