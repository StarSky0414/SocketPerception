package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.bean.service.UserInfoRequestBean;
import com.tts.starsky.apperceive.chaui.bean.OtherUserInfoQuery;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.Guard;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropSquareTransformation;

import static java.security.AccessController.getContext;

public class OtherUserActivity extends BaseActivity {

    private ImageView bt_portrait;
    private ImageView show_1;
    private ImageView show_2;
    private ImageView show_3;
    private ImageView show_4;
    private ImageView show_5;
    private ImageView show_6;
    private TextView tv_name;
    private ArrayList showArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        viewInit();
        String userId = getIntent().getStringExtra("userId");
        UserInfoRequestBean userInfoRequestBean = new UserInfoRequestBean(userId);
        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.OTHER_USERINFO, userInfoRequestBean);
    }

    public void viewInit(){
        bt_portrait = findViewById(R.id.bt_portrait);
        tv_name = findViewById(R.id.tv_name);

        showArray = new ArrayList<View>();

        show_1 = findViewById(R.id.show_1);
        showArray.add(show_1);
        show_2 = findViewById(R.id.show_2);
        showArray.add(show_2);
        show_3 = findViewById(R.id.show_3);
        showArray.add(show_3);
        show_4 = findViewById(R.id.show_4);
        showArray.add(show_4);
        show_5 = findViewById(R.id.show_5);
        showArray.add(show_5);
        show_6 = findViewById(R.id.show_6);
        showArray.add(show_6);




    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sycnMessage(OtherUserInfoQuery otherUserInfoQuery){
        String nickName = otherUserInfoQuery.getNickName();
        tv_name.setText(nickName);
        String photoUser = otherUserInfoQuery.getPhotoUser();
        Glide.with(this).load(photoUser).apply(RequestOptions.circleCropTransform()).into(bt_portrait);
        List<String> userTrendsPhotoList = otherUserInfoQuery.getUserTrendsPhoto();
        for (int i =0 ;i<userTrendsPhotoList.size();i++){
            ImageView show = (ImageView) showArray.get(i);
            RequestOptions options=RequestOptions.bitmapTransform(new CropSquareTransformation(this)).centerCrop().override(300, 300);
            Glide.with(this)
                    .load("https://thethreestooges.oss-cn-shenzhen.aliyuncs.com/"+userTrendsPhotoList.get(i))
                    .apply(options)
                    .into(show);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
