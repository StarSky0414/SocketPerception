package com.tts.starsky.apperceive.controller.service;
import org.junit.Test;

import java.util.HashMap;

public class LoginServiceTest {

    @Test
    public void testRequrst() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userPhone",1+"");
        stringStringHashMap.put("password",11+"");
//        loginService.pushMessageAuto(stringStringHashMap,ServerUrl.userLogin(),null,HandlerSigin.serverIdentityVerification);
//        HttpRequest httpRequest = new HttpRequest();
//        httpRequest.postRequest(stringStringHashMap, ServerUrl.userLogin(), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response);
//            }
//        });

    }
}