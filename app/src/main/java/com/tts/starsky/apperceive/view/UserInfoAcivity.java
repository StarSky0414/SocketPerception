package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tts.starsky.apperceive.R;

public class UserInfoAcivity  extends Activity implements View.OnClickListener {

    private Button bt_update_user_info;
    private EditText password;
    private EditText et_again_password;
    private EditText et_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_updatepassword);

        bt_update_user_info = findViewById(R.id.bt_update_user_info);
        Button viewById = (Button)findViewById(R.id.bt_update_user_info);
        bt_update_user_info.setOnClickListener(this);

        et_nickname = (EditText) findViewById(R.id.et_nickname);
        password = (EditText)findViewById(R.id.password);
        et_again_password = (EditText)findViewById(R.id.et_again_password);
    }


    @Override
    public void onClick(View v) {
        System.out.println("onClick");
        switch(v.getId()){
            case R.id.bt_update_user_info:
                System.out.println("bt_update_user_info");
                Intent intent = getIntent();
                String name = intent.getStringExtra("phoneNumber");
                String nickName=et_nickname.getText().toString();
                String password = et_again_password.getText().toString();
//                UserInfoControl userInfoControl = new UserInfoControl(this);
//                userInfoControl.pushRequestUserInfo(name,nickName,password,HandlerSigin.serverUpdateUserInfo);
                break;
        }
    }
}
