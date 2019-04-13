package com.tts.starsky.apperceive.controller;


import com.tts.starsky.apperceive.bean.tometeor.AdapterRequestBean;
import com.tts.starsky.apperceive.bean.tometeor.AdapterResponseBean;
import com.tts.starsky.apperceive.utiles.Conversion;

import java.io.*;


public class Distribute {

    /**
     * 解析数据包
     * 封装格式 预留位置【20】 分发位置长度【4】  分发位置【20】 数据包长度【4】 json【数据包实际数值】
     *
     * @param inputStream 客户端输入流
     * @throws IOException
     */
    public AdapterRequestBean resolvePackage(InputStream inputStream) throws IOException {
        byte[] packLengthByte = new byte[8];
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        //读取json长度
        dataInputStream.read(packLengthByte);
        int i = Integer.valueOf(new String(packLengthByte));
        byte[] messageBodyByte = new byte[i];
        //读取数据json
//        int read = dataInputStream.read(messageBodyByte);


        //======================
        //  更新读取方式
        //======================

        byte[] bytesTemp = new byte[1000];
        StringBuffer jsonStringBuff = new StringBuffer();
        int longBytesTemp;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String jsonString = bufferedReader.readLine();
//        while ((longBytesTemp= dataInputStream.read(bytesTemp))!=-1){
//
//
////            i-=(longBytesTemp= dataInputStream.read(bytesTemp));
//            String s = new String(bytesTemp, "utf-8");
//            System.out.println("=======! s"+s);
//            jsonStringBuff.append(s);
//            System.out.println("!=========read : "+longBytesTemp);
////            if (i<=0){
////                i-=(longBytesTemp= dataInputStream.read(bytesTemp));
////                jsonStringBuff.append(new String(bytesTemp));
////                break;
////            }
//        }
//        String jsonString = new String(jsonStringBuff);

        if (jsonString == null || jsonString.length()==0){
            return  new AdapterRequestBean("{}");
        }
//        String jsonString = new String(messageBodyByte);
        AdapterRequestBean adapterRequestBean = new AdapterRequestBean(jsonString);
        System.out.println("========================"+i);
//        System.out.println("========================"+read);
        System.out.println("========================"+jsonString);
        return adapterRequestBean;
    }

    /**
     * 将json 进行数据封装
     * 封装格式 预留位置【20】 分发位置长度【4】  分发位置【20】 数据包长度【4】 json【数据包实际数值】
     *
     * @param adapterResponseBean   服务器返回json
     * @param outputStream client输出流
     * @throws IOException
     */
    public void toEncapsulation(OutputStream outputStream, AdapterResponseBean adapterResponseBean) throws IOException {

        String jsonString = adapterResponseBean.getJsonString();
        String pathString = adapterResponseBean.getPath();
//        byte[] obligateBytes = new byte[20];
        byte[] pathStringBytes = pathString.getBytes();
        byte[] jsonStringBytes = jsonString.getBytes("unicode");

        int pathLength =pathStringBytes.length;
        int packLength =jsonStringBytes.length;

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        byte[] packLengthByte = Conversion.intToByteArray(packLength);
        byte[] pathLengthByte = Conversion.intToByteArray(pathLength);

//        dataOutputStream.write(obligateBytes);
        dataOutputStream.write(pathLengthByte);
        dataOutputStream.write(pathStringBytes);
        dataOutputStream.write(packLengthByte);
        dataOutputStream.write(jsonStringBytes);
        dataOutputStream.flush();
    }

}
