package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.controller.MyOkHttp;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginRegisterManager implements Callback {

    private MyOkHttp okHttp = new MyOkHttp();

    public LoginRegisterManager() {
    }

    public void loginRequest(String userPhone , String passWord){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userPhone",userPhone);
        hashMap.put("password",passWord);
        System.out.println("=======loginRequest is post");

        okHttp.requets(MyOkHttp.LOGIN_ADDRESS,hashMap,this);

    }


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        System.out.println("=======EventBus is post");
        EventBus.getDefault().post(response);
    }

    public interface MyCallback{
        void onResponse(Response response) throws IOException;
    }

}
