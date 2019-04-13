package com.tts.starsky.apperceive.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.MainActivity;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.manager.LoginRegisterManager;
import com.tts.starsky.apperceive.manager.MessageServiceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import okhttp3.Response;


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginRegisterManager.MyCallback {
    private static Button submit;
    private static EditText et_login;
    private static EditText et_password;
    private LoginRegisterManager loginRegisterManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.bt_submit);
        this.submit.setOnClickListener(this);

        et_login = (EditText) findViewById(R.id.login);
        et_password = (EditText) findViewById(R.id.password);

        loginRegisterManager = new LoginRegisterManager();

    }

    public void login(View view) {

    }


    public void toRegister(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
//                Intent intent = new Intent(this, UseInFragment.class);
//                startActivity(intent);
                String userPhone = et_login.getText().toString();
                String password = et_password.getText().toString();

                loginRegisterManager.loginRequest(userPhone,password);
                //LoginControl loginControl = new LoginControl(this);
//                loginControl.pushUserAndPassword(userPhone,password,HandlerSigin.serverIdentityVerification);
//
//                Intent intent = new Intent(this,FragmentMain.class);
//                startActivity(intent);

        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onResponse(Response response) throws IOException {
        Toast.makeText(this, "请求成功", Toast.LENGTH_SHORT).show();
        String string = response.body().string();
        System.out.println(string);
        JSONObject jsonObject = JSON.parseObject(string);
        Integer message = jsonObject.getInteger("message");
        System.out.println(message);
        if (message == 0){
            Toast.makeText(this, "请重新输入账户或密码！", Toast.LENGTH_SHORT).show();
        }else {
            String tocken = jsonObject.getString("tocken");
            UserStateInfo.setClientSession(tocken);

            String userIdOld = UserStateInfo.getUserId();
            String userId = jsonObject.getString("userId");
            UserStateInfo.setUserId(userId);
            if(userIdOld != null && !userIdOld.equals("") && !userId.equals(userIdOld)){
                UserStateInfo.setUserClientMessageId("0");
//                DBBase.dbBaseinit(this);
                UserStateInfo.setUnReadMassageNum(0);
            }

            MessageServiceManager.messageUpdateTaskInit(5);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}