package com.tts.starsky.apperceive.controller;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.IdentityOutTime;
import com.tts.starsky.apperceive.bean.service.SeviceBean;
import com.tts.starsky.apperceive.bean.tometeor.AdapterRequestBean;
import com.tts.starsky.apperceive.service.callback.IMyCallBack;
import com.tts.starsky.apperceive.utiles.Conversion;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.*;
import java.net.Socket;

public class MessageSend implements Runnable{

    private IMyCallBack iMyCallBack;
    private String pathString;
    private String jsonString;

//    public static String hostAddress="120.25.96.141";
    public static String hostAddress="172.20.7.59";

    private static final int port=8090;

    private static final String tempSession = "e17e7ddee0804859af9d3787345a405b";
    public MessageSend(String pathString, SeviceBean seviceBean, IMyCallBack iMyCallBack) {
        this.pathString=pathString;
        this.iMyCallBack=iMyCallBack;
        if (seviceBean==null){
            jsonString = "{}";
        }else {
            jsonString = JSONObject.toJSONString(seviceBean);
        }
    }

    public void messageSend() throws IOException {
        System.out.println("============== is run");
        Socket socket = new Socket(hostAddress, port);
        OutputStream outputStream = socket.getOutputStream();

        byte[] pathStringBytes = pathString.getBytes();
        byte[] jsonStringBytes = jsonString.getBytes("utf-8");

        int pathLength =pathStringBytes.length;
        int packLength =jsonStringBytes.length;

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        byte[] packLengthByte = Conversion.intToByteArray(packLength);
        byte[] pathLengthByte = Conversion.intToByteArray(pathLength);
        UserStateInfo userStateInfo = new UserStateInfo();
        System.out.println("============UserStateInfo.getClientSession().getBytes() : "+userStateInfo.getClientSession());
        dataOutputStream.write(userStateInfo.getClientSession().getBytes());
        dataOutputStream.write(pathLengthByte);
        dataOutputStream.write(pathStringBytes);
        dataOutputStream.write(packLengthByte);
        dataOutputStream.write(jsonStringBytes);
        dataOutputStream.flush();

        byte[] sessionByte = new byte[32];
        InputStream inputStream = socket.getInputStream();
        inputStream.read(sessionByte);
        String s = new String(sessionByte);
        userStateInfo.setClientSession(s);

        System.out.println("================UserStateInfo.getClientSession()："+userStateInfo.getClientSession());
        Distribute distribute = new Distribute();
        AdapterRequestBean adapterRequestBean = distribute.resolvePackage(inputStream);
        String requestJsonString = adapterRequestBean.getJsonString();
        System.out.println(requestJsonString);

        dataOutputStream.close();
        outputStream.close();
        socket.close();
        try {
            iMyCallBack.callBack(requestJsonString);
        } catch (JSONException e) {
            EventBus.getDefault().post(new IdentityOutTime());
        }
    }

    @Override
    public void run() {
        try {
            messageSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
