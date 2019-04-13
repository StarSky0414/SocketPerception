package com.tts.starsky.apperceive;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.config.BottomNavigationConfig;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.manager.MainActivityManager;
import com.tts.starsky.apperceive.manager.MessageServiceManager;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.view.BaseActivity;
import com.tts.starsky.apperceive.view.LoginActivity;
import com.tts.starsky.apperceive.view.fragment.FindFragment;
import com.tts.starsky.apperceive.view.fragment.MyFragment;
import com.tts.starsky.apperceive.view.fragment.MessageFragment;
import com.tts.starsky.apperceive.view.fragment.TrendFragment;

/**
 * Created by 武当山道士 on 2017/8/16.
 */

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private MainActivityManager mainActivityManager;
    private BottomNavigationConfig bottomNavigationConfig;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 身份验证在绘制页面前
//        checkStatus();

//        String userId = UserStateInfo.getUserId();
//        String userClientMessageId = UserStateInfo.getUserClientMessageId();
//        SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userClientMessageId);
//        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE, syncMessageRequestBean);
//

        // 初始化Manager
        mainActivityManager = new MainActivityManager(this);
        // 绘制页面
        setContentView(R.layout.activity_main);
        // 初始化底部导航栏
        bottomNavigationConfig = new BottomNavigationConfig(this);
        bottomNavigationConfig.ButtonNavigationConfigInit();
        // 设置默认导航栏
        setDefaultFragment();



    }

    /**
     * 设置默认导航栏
     */
    @SuppressLint("NewApi")
    private void setDefaultFragment() {
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.tb, FindFragment.newInstance());
        transaction.commit();
    }

    /**
     * 设置导航选中的事件
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onTabSelected(int position) {
        Log.d("===================", "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                transaction.replace(R.id.tb, FindFragment.newInstance());
//
                break;
            case 1:
                transaction.replace(R.id.tb, MessageFragment.newInstance());
                break;
            case 2:
                transaction.replace(R.id.tb, TrendFragment.newInstance());
                break;
            case 3:
                transaction.replace(R.id.tb, MyFragment.newInstance());
                break;
            default:
                break;
        }
        transaction.commit();// 事务提交
    }

    /**
     * 设置未选中Fragment 事务
     */
    @Override
    public void onTabUnselected(int position) {
//        Toast.makeText(this, "未选中" + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {
//        Toast.makeText(this, "释放" + position, Toast.LENGTH_SHORT).show();
    }

    public void changeButtonNum(long number) {
//        Toast.makeText(this, "有新消息" + number, Toast.LENGTH_SHORT).show();
        bottomNavigationConfig.addNumber(number);
    }



}