package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tts.starsky.apperceive.MainActivity;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.SplashScreenApplication;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.oss.InitOssClient;
import com.tts.starsky.apperceive.oss.OSSConfig;
import com.tts.starsky.apperceive.service.MyService;


/**
 * 那当自己都萎靡到
 * 无法被依靠的时候该如何振作？
 * .
 * 除过自己心中笃信的那一点不灭的光亮
 * 我觉得这世间再没有别的东西比它值得被如此依靠。
 * .
 * Created by Aran on 2018/12/25.
 */


public class SplashActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //添加数据库动态访问插件
//        SqlScoutServer.create(this, getPackageName());
        ImageView layoutSplash = findViewById(R.id.iv_launch);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(1500);//设置动画播放时长1000毫秒（1秒）
        layoutSplash.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                UserStateInfo.init(SplashActivity.this);
//                DBBase.dbBaseinit(SplashActivity.this);
//                InitOssClient.initOssClient(SplashActivity.this, OSSConfig.stsServer, OSSConfig.endPoint);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkStatus();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });

    }

    private void checkStatus() {
        //======================
        //  进行身份验证 无session的
        //  跳转回登陆页面
        //======================
        String clientSession = UserStateInfo.getClientSession();
        String userId = UserStateInfo.getUserId();
        if (userId.equals("0")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
