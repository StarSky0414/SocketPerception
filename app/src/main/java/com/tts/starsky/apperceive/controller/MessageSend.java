package com.tts.starsky.apperceive.controller;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.tts.starsky.apperceive.bean.service.SeviceBean;
import com.tts.starsky.apperceive.bean.tometeor.AdapterRequestBean;
import com.tts.starsky.apperceive.service.callback.IMyCallBack;
import com.tts.starsky.apperceive.utiles.Conversion;

import java.io.*;
import java.net.Socket;

public class MessageSend implements Runnable{

    private IMyCallBack iMyCallBack;
    private String pathString;
    private String jsonString;

    private static String hostAddress="192.168.1.102";
//    private static String hostAddress="192.168.43.212";
    private static final int port=8090;

    private static final String tempSession = "f2634392b55544208704edd0cd1cd6a5";

    public MessageSend(String pathString, SeviceBean seviceBean, IMyCallBack iMyCallBack) {
        this.pathString=pathString;
        this.iMyCallBack=iMyCallBack;
        jsonString = JSONObject.toJSONString(seviceBean);
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
        dataOutputStream.write(tempSession.getBytes());
        dataOutputStream.write(pathLengthByte);
        dataOutputStream.write(pathStringBytes);
        dataOutputStream.write(packLengthByte);
        dataOutputStream.write(jsonStringBytes);
        dataOutputStream.flush();


        byte[] sessionByte = new byte[32];
        InputStream inputStream = socket.getInputStream();
        inputStream.read(sessionByte);

        String s = new String(sessionByte);
        System.out.println("================s："+s);
        Distribute distribute = new Distribute();
        AdapterRequestBean adapterRequestBean = distribute.resolvePackage(inputStream);
        String requestJsonString = adapterRequestBean.getJsonString();
        System.out.println(requestJsonString);

        dataOutputStream.close();
        outputStream.close();
        socket.close();
        iMyCallBack.callBack(requestJsonString);
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
