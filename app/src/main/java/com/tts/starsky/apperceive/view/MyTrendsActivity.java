package com.tts.starsky.apperceive.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.TrendsListItemBean;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnTrendFlush;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.controller.adapter.TrendsListAdapter;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的动态管理展示
 */
public class MyTrendsActivity  extends Activity{

    private AlertDialog.Builder builder;
    // 定义
//    private XRecyclerView mRecyclerView;
    private TrendsListAdapter mAdapter;
    private XRecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trends);


        EventBus.getDefault().register(this);

        List<TrendsListItemBean> dataList = new ArrayList<>();
        mAdapter = new TrendsListAdapter(this, dataList);
        mAdapter.setMyBinder(myBinder);

        init();

        Intent intentServer = new Intent(this, MyService.class);
        bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);


    }

    /**
     * 初始化界面
     */
    private void init() {


        //======================
        //  初始化 RecyclerView
        //======================

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (XRecyclerView) findViewById(R.id.rl_recyclerview);
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
                SyncTrendsBean syncTrendsBean = new SyncTrendsBean(UserStateInfo.getUserId(), null);
                myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);
            }

            @Override
            public void onLoadMore() {
                SyncTrendsBean syncTrendsBean = new SyncTrendsBean(UserStateInfo.getUserId(), String.valueOf(mAdapter.getMinId()));
                myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_LOAD, syncTrendsBean);
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_user_nick:
//            case R.id.iv_user_head_photo:
////                Toast.makeText(this, "onClick    " + i + "    " + dataList.get(i).getSendUserId(), Toast.LENGTH_SHORT).show();
//                Toast
//                Intent intent = new Intent(this, OtherUserActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.bt_trend_my_dele:
//                //    显示出该对话框
//                builder.show();
////                if (dialogSign == DialogSign.S){
////                    SendTrendsBean sendTrendsBean = new SendTrendsBean();
////                    TrendsListItemBean trendsListItemBean = dataList.get();
////                    sendTrendsBean.setId(trendsListItemBean.getId());
////                    myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_DELETE,sendTrendsBean);
////                }
//                break;
//        }
//    }


    /**
     *   服务调用
     */
    private MyBinder myBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBinder) service;
            mAdapter.setHideMy();


            // test
            UserStateInfo.setUserId("1");

            SyncTrendsBean syncTrendsBean = new SyncTrendsBean(UserStateInfo.getUserId(), null);
            System.out.println("syncTrendsBean: ============= "+syncTrendsBean.toString());
            myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendCreateCallBack(SycnTrendFlush sycnTrendFlush) {
        List<SendTrendsBean> sendTrendsBeanList = sycnTrendFlush.getSendTrendsBeanList();
        ArrayList<TrendsListItemBean> dataList = new ArrayList<>();
        for (SendTrendsBean sendTrendsBean : sendTrendsBeanList) {
            System.out.println("sendTrendsBeanList ========== : " + sendTrendsBean.toString());
            TrendsListItemBean trendsListItemBean = new TrendsListItemBean(sendTrendsBean.getId(),sendTrendsBean.getSendUserId(), sendTrendsBean.getContent(), "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/" + sendTrendsBean.getUrl());
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
    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendDeleCallBack(SendTrendsBean sendTrendsBean) {
        System.out.println("===   SendTrendDeleCallBack  ============");
        myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_DELETE, sendTrendsBean);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
////        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }


}
