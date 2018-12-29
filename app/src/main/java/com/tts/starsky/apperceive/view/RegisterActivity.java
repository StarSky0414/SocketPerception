package com.tts.starsky.apperceive.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tts.starsky.apperceive.R;

//////import com.tts.starsky.apperceive.controller.communication.LoginControl;

public class RegisterActivity extends MyActivity implements View.OnClickListener {

    private EditText et_phone_number;
    private EditText et_register_num;
    private Button bt_get_register_num;
    private Button bt_submit_register;
//    private LoginControl loginControl;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_register_num = (EditText) findViewById(R.id.et_register_num);
        bt_get_register_num = (Button) findViewById(R.id.bt_get_register_num);
        bt_submit_register = (Button) findViewById(R.id.bt_submit_register);
        this.bt_get_register_num.setOnClickListener(this);
        this.bt_submit_register.setOnClickListener(this);



//        loginControl =new LoginControl(this,bt_get_register_num,bt_submit_register);
    }


    @Override
    public void onClick(View v) {
        System.out.println("onClick");
        switch (v.getId()) {
            case R.id.bt_get_register_num:
                System.out.println("bt_get_register_num");
//                loginControl.pushRequestPhoneNumber(et_phone_number.getText().toString(),HandlerSigin.serverPhoneNumber);
                break;
            case R.id.bt_submit_register:
                System.out.println("bt_submit_register");
//                loginControl.pushRequestRegisterNumber(et_register_num.getText().toString(),et_phone_number.getText().toString(),HandlerSigin.serverRegisterNumber);
                break;
        }
    }

}
