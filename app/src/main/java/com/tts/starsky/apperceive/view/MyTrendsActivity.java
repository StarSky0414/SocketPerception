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
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
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
public class MyTrendsActivity extends Activity {

    private AlertDialog.Builder builder;
    // 定义
//    private XRecyclerView mRecyclerView;
    private TrendsListAdapter mAdapter;
    private XRecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trends);
        findViewById(R.id.tv_top_trend_right).setVisibility(View.GONE);

        EventBus.getDefault().register(this);

        List<TrendsListItemBean> dataList = new ArrayList<>();
        mAdapter = new TrendsListAdapter(this, dataList);

        SyncTrendsBean syncTrendsBean = new SyncTrendsBean(UserStateInfo.getUserId(), null);
        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);

        init();

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
        mRecyclerView.setLoadingMoreEnabled(false);
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
                LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);

            }

            @Override
            public void onLoadMore() {
//                SyncTrendsBean syncTrendsBean = new SyncTrendsBean(UserStateInfo.getUserId(), String.valueOf(mAdapter.getMinId()));
//                LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.TRENDS_LOAD, syncTrendsBean);
            }
        });
    }


    @SuppressLint("NewApi")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendCreateCallBack(SycnTrendFlush sycnTrendFlush) {
        List<SendTrendsBean> sendTrendsBeanList = sycnTrendFlush.getSendTrendsBeanList();
        ArrayList<TrendsListItemBean> dataList = new ArrayList<>();
        for (SendTrendsBean sendTrendsBean : sendTrendsBeanList) {
            System.out.println("sendTrendsBeanList ========== : " + sendTrendsBean.toString());
            TrendsListItemBean trendsListItemBean = new TrendsListItemBean(Integer.valueOf(sendTrendsBean.getTrendId()), sendTrendsBean.getSendUserId(), sendTrendsBean.getTrendContent(), "https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/" + sendTrendsBean.getTrendPhotoUrl());
            trendsListItemBean.setUserPhoto(sendTrendsBean.getHeadPhotoUrl());
            trendsListItemBean.setNickName(sendTrendsBean.getUserNickName());
            dataList.add(trendsListItemBean);
        }

        SycnTrendFlush.StatSign statSign = sycnTrendFlush.getStatSign();
        switch (statSign) {
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
        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.TRENDS_DELETE, sendTrendsBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
