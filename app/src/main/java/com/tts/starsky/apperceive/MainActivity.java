package com.tts.starsky.apperceive;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.exception.DBException;
import com.tts.starsky.apperceive.oss.InitOssClient;
import com.tts.starsky.apperceive.oss.OSSConfig;
import com.tts.starsky.apperceive.view.fragment.MyFragment;
import com.tts.starsky.apperceive.view.fragment.MessageFragment;
import com.tts.starsky.apperceive.view.fragment.TrendFragment;
import com.tts.starsky.apperceive.view.fragment.UnderButtonState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 武当山道士 on 2017/8/16.
 */

public class MainActivity extends Activity implements BottomNavigationBar.OnTabSelectedListener {

    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private String TAG = MainActivity.class.getSimpleName();
    private MessageFragment mScanFragment;
    private HomeFragment mHomeFragment;
    private BottomNavigationItem bottomNavigationItem;
    private TextBadgeItem numberBadge;
    UserStateInfo userStateInfo;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

//            MyBinder service1 = (MyBinder) service;
//            System.out.println("========== onServiceConnected");
//            UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
//            UserStateBean userStateBean = userStateDBProvider.queryUserState();
//            String userLastMessageId = userStateBean.getUserLastMessageId();
//            String userId = userStateBean.getUserId();
//            SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userLastMessageId);
//            service1.adapterExceptionDispose(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitOssClient.initOssClient(this,OSSConfig.stsServer,OSSConfig.endPoint);
        UserStateInfo userStateInfo = new UserStateInfo();
        userStateInfo.setUserId("1");
        EventBus.getDefault().register(this);
//        DBBase.dbBaseinit(this);
//        EventBus.getDefault().register(this);
//        Intent serviceIntent = new Intent(MainActivity.this, MessageService.class);
//        startService(serviceIntent);
//        myService.setJsonString(xxxx);

//        DBBase.dbBaseinit(this);
//        Intent intent = new Intent(MainActivity.this, MyService.class);
//        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
//
//        DaoSession dbSession = null;
//        try {
//            dbSession = DBBase.getDBBase().getDBSession();
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//        String userLastMessageId = dbSession.getUserStateBeanDao().queryBuilder().unique().getUserLastMessageId();
//        System.out.println("========================userLastMessageId:"+userLastMessageId);
//        myService.adapter(EvenBusEnumService.SEND_MESSAGE);
//        DBTest dbTest = new DBTest();
//        dbTest.creat(this);

        /**
         * bottomNavigation 设置
         */

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#1ccbae");//导航栏背景色
        /** 添加导航按钮 */

        numberBadge = new TextBadgeItem()
                .setBorderWidth(0)
                .setBackgroundColorResource(R.color.messageRed)
                .setText("3")
                .setHideOnSelect(true);

        bottomNavigationItem = new BottomNavigationItem(R.drawable.find2, "消息").setBadgeItem(numberBadge);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.find1, "发现"))

                .addItem(bottomNavigationItem)
                .addItem(new BottomNavigationItem(R.drawable.trends, "动态"))
                .addItem(new BottomNavigationItem(R.drawable.my2, "我de"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .setBarBackgroundColor(R.color.black)
                .initialise(); //initialise 一定要放在 所有设置的最后一项
//        setDefaultFragment();//设置默认导航栏

    }

    /**
     * 设置默认导航栏
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = HomeFragment.newInstance("首页");
        transaction.replace(R.id.tb, mHomeFragment);
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
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance("首页");
                }
                Toast.makeText(this, "bbbbbbbbbbbbbbb", Toast.LENGTH_SHORT).show();
                Log.d("===================", "HomeFragment.newInstance(\"首页\")");
                transaction.replace(R.id.tb, mHomeFragment);
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
        Toast.makeText(this, "未选中" + position, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {
        numberBadge.setText("10");
//        bottomNavigationItem.setBadgeItem(numberBadge);

        Toast.makeText(this, "释放" + position, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UnderButtonState underButtonState) {
        System.out.println("====================   "+UnderButtonState.getUnReadAllNum());
        if (UnderButtonState.getUnReadAllNum() == 0) {
            numberBadge.setBackgroundColorResource(R.color.messageNull);
            numberBadge.setTextColor(R.color.messageNull);

        } else {
            numberBadge.setText(String.valueOf(UnderButtonState.getUnReadAllNum()));
        }
        Toast.makeText(this, "aaaaa", Toast.LENGTH_SHORT).show();
        Log.w("========", "run");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpdateSign sendToSever) {
        DaoSession dbSession =null;
        try {
             dbSession= DBBase.getDBBase().getDBSession();
        } catch (DBException e) {
            e.printStackTrace();
        }
        long count = dbSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.Readed.eq(0)).count();
        Toast.makeText(this, "有新消息"+count, Toast.LENGTH_SHORT).show();
        numberBadge.setText(String.valueOf(count));
    }



}