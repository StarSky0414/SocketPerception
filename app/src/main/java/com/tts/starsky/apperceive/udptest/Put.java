package com.tts.starsky.apperceive.udptest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Put implements Runnable {
    private final DatagramSocket datagramSocket;

    public Put(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {
        //发送端

//        //建立udp的服务
//        DatagramSocket datagramSocket = null;
//        try {
//            datagramSocket = new DatagramSocket(port);
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
        //准备数据，把数据封装到数据包中。
        String data = String.valueOf("这是我发送的数据");
        //创建了一个数据包
        DatagramPacket packet = null;
        try {
            InetAddress byName = InetAddress.getByName("172.20.7.59");
            System.out.println("==================byNmae"+byName.getAddress());
            packet = new DatagramPacket(data.getBytes(), data.getBytes().length,byName , 9090);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        //调用udp的服务发送数据包
        try {
            datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        //关闭资源 ---实际上就是释放占用的端口号
//        datagramSocket.close();

    }

}
