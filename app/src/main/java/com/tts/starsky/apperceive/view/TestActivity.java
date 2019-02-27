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
////
////        Glide.with(this).
////                load(R.drawable.test_photo).
////                bitmapTransform(new CropSquareTransformation(this)).
////                into(imageView);
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tts.starsky.apperceive.MainActivity;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.oss.InitOssClient;
import com.tts.starsky.apperceive.oss.OSSConfig;
import com.tts.starsky.apperceive.oss.UpFile;
import com.tts.starsky.apperceive.service.MyService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class TestActivity extends Activity implements View.OnClickListener {


    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends_item);

        init();

        String ipAddress = getIPAddress(this);
        System.out.println("================="+ipAddress);
    }

    /**
     * 初始化界面
     */
    private void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_trend_my_dele:
                //    显示出该对话框
                builder.show();
                break;
        }
    }


    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }


            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }


    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}