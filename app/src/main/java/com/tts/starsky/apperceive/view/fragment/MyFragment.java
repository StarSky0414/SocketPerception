package com.tts.starsky.apperceive.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.chaui.bean.Message;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.UserInfoBeanDao;
import com.tts.starsky.apperceive.db.bean.UserInfoBean;
import com.tts.starsky.apperceive.manager.MessageServiceManager;
import com.tts.starsky.apperceive.view.AboutUs;
import com.tts.starsky.apperceive.view.LoginActivity;
import com.tts.starsky.apperceive.view.MyTrendsActivity;
import com.tts.starsky.apperceive.view.SendTrendActivity;

import org.greenrobot.eventbus.EventBus;

public class MyFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "===================MyFragment: ";
    private ImageView bt_portrait;
    private TextView tv_name;

    public static MyFragment newInstance() {
        MyFragment my = new MyFragment();
        return my;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.activity_my, container, false);
        RelativeLayout viewById = inflate.findViewById(R.id.rl_my_trends);
        RelativeLayout rl_about_us = inflate.findViewById(R.id.rl_about_us);
        RelativeLayout update_s = inflate.findViewById(R.id.update_s);
        Button bt_logout = inflate.findViewById(R.id.bt_logout);
        bt_portrait = inflate.findViewById(R.id.bt_portrait);
        tv_name = inflate.findViewById(R.id.tv_name);
        update_s = inflate.findViewById(R.id.update_s);
        bt_logout.setOnClickListener(this);
        viewById.setOnClickListener(this);
        rl_about_us.setOnClickListener(this);
        queryUserInfo();
        Log.d("============", "=========");
        return inflate;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_my_trends:
                //这里是弹出一个消息---"按钮被点击"
                Intent intent1 = new Intent(getActivity(), MyTrendsActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_about_us:
                //这里是弹出一个消息---"按钮被点击"
                Intent intent2 = new Intent(getActivity(), AboutUs.class);
                startActivity(intent2);
                break;
            case R.id.bt_logout:
                MessageServiceManager.stopTask();
                UserStateInfo.setClientSession("");
                Intent intent3 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent3);
                getActivity().finish();
                break;
            case R.id.update_s:
                Toast.makeText(getActivity(), "当前软件为最新版本", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void queryUserInfo() {
        DBBase dbBase = DBBase.getDBBase();
        UserInfoBeanDao userInfoBeanDao = dbBase.getDBSession().getUserInfoBeanDao();
        UserInfoBean unique = userInfoBeanDao.queryBuilder().where(UserInfoBeanDao.Properties.Id.eq(UserStateInfo.getUserId())).unique();
        if (unique == null) {
            return;
        }
        String photoUser = unique.getPhotoUser();
        String nickName = unique.getNickName();
        Glide.with(this).load(photoUser).into(bt_portrait);
        tv_name.setText(nickName);
    }
}
