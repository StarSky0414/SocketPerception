package com.tts.starsky.apperceive.view;//package com.tts.starsky.apperceive.view;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.DocumentsContract;
//import android.provider.MediaStore;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.tts.starsky.apperceive.R;
//import com.tts.starsky.apperceive.oss.OSSInit;
//import com.tts.starsky.apperceive.oss.UpFile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import jp.wasabeef.glide.transformations.CropSquareTransformation;
//
//public class TestActivity extends Activity {
//    private Uri uri;
//    //    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.active_send_trend);
////    }
//
//    // For a simple view:
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        OSSInit.init(this);
//        setContentView(R.layout.active_test_gld);
//        obtainPermission();
//
////        ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
//////
//Glide.with(this).
//        load(R.drawable.test_photo).
//        bitmapTransform(new CropSquareTransformation(this)).
//        into(imageView);
//
//        goAlbums();
//
//    }
//
//    int SELECT_PIC = 33;
//
//    /**
//     * 调用相册
//     */
//    private void goAlbums() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        startActivityForResult(intent, SELECT_PIC);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SELECT_PIC) {
//            System.out.println("=================================!!!!!!!ok");
//            uri = data.getData();
//            System.out.println("=========================" + uri.getPath());
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            UpFile upFile = new UpFile();
//                            upFile.upfile(uri.getPath());
//                        }
//                    }
//            ).start();
//
//        }
//    }
//
//    // For a simple image list:
////    @Override
////    public View getView(int position, View recycled, ViewGroup container) {
////        final ImageView myImageView;
////        if (recycled == null) {
////            myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
////        } else {
////            myImageView = (ImageView) recycled;
////        }
////
////        String url = myUrls.get(position);
////
////        Glide
////                .with(myFragment)
////                .load(url)
////                .centerCrop()
////                .placeholder(R.drawable.loading_spinner)
////                .crossFade()
////                .into(myImageView);
////
////        return myImageView;
////    }
//
//
//    //调用系统相册-选择图片
//    private static final int IMAGE = 1;
//    //所需权限
////    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////    }
////
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //获取图片路径
//        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumns = {MediaStore.Images.Media.DATA};
//            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
//            c.moveToFirst();
//            int columnIndex = c.getColumnIndex(filePathColumns[0]);
//            String imagePath = c.getString(columnIndex);
//            showImage(imagePath);
//            c.close();
//        }
//    }
//
//
//
//}

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.service.EvenBusEnumService;

import java.io.IOException;
import java.net.DatagramSocket;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestActivity extends Activity implements View.OnClickListener {

    private int port;
    DatagramSocket socket = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_test_gld);
        Button put = (Button) findViewById(R.id.put);
        put.setOnClickListener(this);
//        //建立udp的服务 ，并且要监听一个端口。
//
//
//        Thread thread = new Thread(new Runnable() {
//
//
//
//            @Override
//            public void run() {
//
//                try {
//                    socket = new DatagramSocket(0);
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
//
//                // 获取端口用于回发
//                port = socket.getPort();
//
//                //准备空的数据包用于存放数据。
//                byte[] buf = new byte[1024];
//                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // 1024
//                //调用udp的服务接收数据
//                try {
//                    socket.receive(datagramPacket); //receive是一个阻塞型的方法，没有接收到数据包之前会一直等待。 数据实际上就是存储到了byte的自己数组中了。
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("接收端接收到的数据："+ new String(buf,0,datagramPacket.getLength())); // getLength() 获取数据包存储了几个字节。
//                System.out.println("receive阻塞了我，哈哈");
////                //关闭资源
////                socket.close();
//            }
//        });
//
//        thread.start();
//        init();

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(MessageUpdateSign sendToSever) {
//        DaoSession dbSession =null;
//        try {
//            dbSession= DBBase.getDBBase().getDBSession();
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//        long count = dbSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.Readed.eq(0)).count();
//        Toast.makeText(this, "有新消息"+count, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.put:
//                testOkHttp3();
//                Put pu = new Put(socket);
//                Thread thread = new Thread(pu);
//                thread.start();
//                System.out.println("PUT is RUN!!!!");

                LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_FINDINFO,null);

        }
    }

    private void testOkHttp3(){

    }
}