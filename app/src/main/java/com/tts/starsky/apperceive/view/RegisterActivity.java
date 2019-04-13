package com.tts.starsky.apperceive.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.manager.RegisterActivityManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity implements View.OnClickListener,RegisterActivityManager.MyCallback {

    private EditText et_phone_number;
    private EditText et_register_num;
    private Button bt_get_register_num;
    private Button bt_submit_register;
    private RegisterActivityManager registerActivityManager;
    //    private LoginControl loginControl;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_register_num = (EditText) findViewById(R.id.et_register_num);
        bt_get_register_num = (Button) findViewById(R.id.bt_get_register_num);
        bt_submit_register = (Button) findViewById(R.id.bt_submit_register);
        this.bt_get_register_num.setOnClickListener(this);
        this.bt_submit_register.setOnClickListener(this);

        registerActivityManager = new RegisterActivityManager();


//        loginControl =new LoginControl(this,bt_get_register_num,bt_submit_register);
    }


    @Override
    public void onClick(View v) {
        System.out.println("onClick");
        switch (v.getId()) {
            case R.id.bt_get_register_num:
                System.out.println("bt_get_register_num");
                String s = et_phone_number.getText().toString();
                registerActivityManager.loginRequest(s);
//                loginControl.pushRequestPhoneNumber(et_phone_number.getText().toString(),HandlerSigin.serverPhoneNumber);
                break;
            case R.id.bt_submit_register:
                System.out.println("bt_submit_register");
//                loginControl.pushRequestRegisterNumber(et_register_num.getText().toString(),et_phone_number.getText().toString(),HandlerSigin.serverRegisterNumber);
                break;
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
            Toast.makeText(this, "请重新输入验证码！", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"验证通过",Toast.LENGTH_SHORT).show();
        }
    }
}
