package com.tts.starsky.apperceive.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.view.fragment.FragmentMain;
//import com.tts.starsky.apperceive.controller.communication.LoginControl;


public class LoginActivity extends MyActivity implements View.OnClickListener {
    private static Button submit;
    private static EditText et_login;
    private static EditText et_password;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submit = (Button) findViewById(R.id.bt_submit);
        this.submit.setOnClickListener(this);

        et_login = (EditText) findViewById(R.id.login);
        et_password = (EditText) findViewById(R.id.password);

    }

    public void login(View view) {

    }



    public void toRegister(View v){
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
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
                //LoginControl loginControl = new LoginControl(this);
//                loginControl.pushUserAndPassword(userPhone,password,HandlerSigin.serverIdentityVerification);

                Intent intent = new Intent(this,FragmentMain.class);
                startActivity(intent);
        }
    }
}