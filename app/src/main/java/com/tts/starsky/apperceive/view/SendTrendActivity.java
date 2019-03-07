package com.tts.starsky.apperceive.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SendTrendCreateBean;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;
import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.oss.OSSConfig;
import com.tts.starsky.apperceive.oss.UpFile;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;


public class SendTrendActivity extends Activity implements View.OnClickListener {

    //调用系统相册-选择图片
    private static final int IMAGE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private EditText et_send_trend;
    private Button bt_send_context;
    private SendTrendsBean sendTrendsBean;
    private Activity activity;
    private ImageView iv_preview_show;
    private MyBinder myBinder;

    //所需权限
//    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentServer = new Intent(this, MyService.class);
        UserStateInfo userStateInfo = new UserStateInfo();
        System.out.println("SendTrendActivity getsession: " + userStateInfo.getClientSession());


        bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);
        setContentView(R.layout.active_send_trend);
        sendTrendsBean = new SendTrendsBean();
        EventBus.getDefault().register(this);
        this.activity = this;
        Button bt_add_photo = findViewById(R.id.bt_add_photo);
        et_send_trend = findViewById(R.id.et_send_trend);
        bt_send_context = findViewById(R.id.bt_send_context);
        iv_preview_show = (ImageView) findViewById(R.id.iv_preview_show);

        bt_add_photo.setOnClickListener(this);
        bt_send_context.setOnClickListener(this);

        Intent intent = getIntent();
        String trendsBeanId = intent.getStringExtra("trendsBeanId");
        Toast.makeText(activity, "trendsBeanId" + trendsBeanId, Toast.LENGTH_SHORT).show();
        if (trendsBeanId != null && !trendsBeanId.equals("")) {
            System.out.println("=================trendsBeanId " + trendsBeanId);
            String trendsBeanContent = intent.getStringExtra("trendsBeanContent");
            String trendsBeanUrl = intent.getStringExtra("trendsBeanUrl");
            sendTrendsBean.setTrendId(trendsBeanId);
            System.out.println("=================trendsBeanContent " + trendsBeanContent);
            et_send_trend.setText(trendsBeanContent);
            Glide.with(this).
                    load(trendsBeanUrl).
                    into(iv_preview_show);
        } else {
            verifyStoragePermissions(this);
        }
    }


    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add_photo:
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                break;
            case R.id.bt_send_context:
                String trendContext = et_send_trend.getText().toString();
                sendTrendsBean.setTrendContent(trendContext);
                UserStateInfo userStateInfo = new UserStateInfo();
                sendTrendsBean.setSendUserId(userStateInfo.getUserId());
                System.out.println("sendTrendsBean.toString() ===============" + sendTrendsBean.toString());
                Toast.makeText(this, "hhh" + trendContext, Toast.LENGTH_SHORT).show();

                if (sendTrendsBean.getTrendId() != null && !sendTrendsBean.getTrendId().equals("")) {
                    myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_UPDATE, sendTrendsBean);
                } else {

                    myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_CREATE, sendTrendsBean);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(final String imaePath) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        iv_preview_show.setImageBitmap(bm);


        new Thread(new Runnable() {
            @Override
            public void run() {
                UpFile upFile = new UpFile();
                UserStateInfo userStateInfo = new UserStateInfo();
                String photoUpPath = OSSConfig.upRootPath + "trend/" + userStateInfo.getUserId() + "/" + System.currentTimeMillis();
                sendTrendsBean.setTrendPhotoUrl(photoUpPath);
                upFile.upfile(imaePath, photoUpPath);
            }
        }).start();
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            myBinder = (MyBinder) service;
            System.out.println("========== onServiceConnected");

//            UserStateDBProvider userStateDBProvider = new UserStateDBProvider();
//            UserStateBean userStateBean = userStateDBProvider.queryUserState();
//            String userLastMessageId = userStateBean.getUserLastMessageId();
//            String userId = userStateBean.getUserId();
//            SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userLastMessageId);
//            service1.adapterExceptionDispose(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SendTrendCreateCallBack(SendTrendCreateBean sendTrendCreateBean) {
        String state = sendTrendCreateBean.getState();
        if (state.equals("1")) {
            Toast.makeText(activity, "发布成功" + state, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(activity, "发布失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
