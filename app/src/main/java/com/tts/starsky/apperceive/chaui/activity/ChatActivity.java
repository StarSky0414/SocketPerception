package com.tts.starsky.apperceive.chaui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.bean.service.SendMessageBean;
import com.tts.starsky.apperceive.bean.service.SyncTrendsBean;
import com.tts.starsky.apperceive.chaui.adapter.ChatAdapter;
import com.tts.starsky.apperceive.chaui.bean.AudioMsgBody;
import com.tts.starsky.apperceive.chaui.bean.FileMsgBody;
import com.tts.starsky.apperceive.chaui.bean.ImageMsgBody;
import com.tts.starsky.apperceive.chaui.bean.Message;
import com.tts.starsky.apperceive.chaui.bean.MsgBody;
import com.tts.starsky.apperceive.chaui.bean.MsgSendStatus;
import com.tts.starsky.apperceive.chaui.bean.MsgType;
import com.tts.starsky.apperceive.chaui.bean.TextMsgBody;
import com.tts.starsky.apperceive.chaui.bean.VideoMsgBody;
import com.tts.starsky.apperceive.chaui.util.ChatUiHelper;
import com.tts.starsky.apperceive.chaui.util.FileUtils;
import com.tts.starsky.apperceive.chaui.util.LogUtil;
import com.tts.starsky.apperceive.chaui.util.PictureFileUtil;
import com.tts.starsky.apperceive.chaui.widget.CircleImageView;
import com.tts.starsky.apperceive.chaui.widget.MediaManager;
import com.tts.starsky.apperceive.chaui.widget.RecordButton;
import com.tts.starsky.apperceive.chaui.widget.StateButton;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.bean.UserInfoBean;
import com.tts.starsky.apperceive.db.bean.UserStateBean;
import com.tts.starsky.apperceive.db.provider.MessageListDBProvider;
import com.tts.starsky.apperceive.exception.DBException;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.service.MyBinder;
import com.tts.starsky.apperceive.service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.bottom_layout)
    RelativeLayout mRlBottomLayout;//表情,添加底部布局
    @BindView(R.id.ivAdd)
    ImageView mIvAdd;
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;
    @BindView(R.id.btn_send)
    StateButton mBtnSend;//发送按钮
    @BindView(R.id.ivAudio)
    ImageView mIvAudio;//录音图片
    @BindView(R.id.btnAudio)
    RecordButton mBtnAudio;//录音按钮
    @BindView(R.id.rlEmotion)
    LinearLayout mLlEmotion;//表情布局
    @BindView(R.id.llAdd)
    LinearLayout mLlAdd;//添加布局
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;//下拉刷新
    private ChatAdapter mAdapter;
    public static final String mSenderId = "right";
    public static final String mTargetId = "left";
    public static final int REQUEST_CODE_IMAGE = 0000;
    public static final int REQUEST_CODE_VEDIO = 1111;
    public static final int REQUEST_CODE_FILE = 2222;
    private LinearLayoutManager mLinearLayout;
    private static UserStateInfo userStateInfo = new UserStateInfo();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EventBus.getDefault().register(this);
        Intent intentServer = new Intent(this, MyService.class);
        bindService(intentServer, serviceConnection, Context.BIND_AUTO_CREATE);

        initContent();
        initUserInfoUI();
        initMessageContent();
    }


    private ImageView ivAudio;

    protected void initContent() {
        ButterKnife.bind(this);
        mAdapter = new ChatAdapter(this, new ArrayList<Message>(), "");
        mLinearLayout = new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(mAdapter);
        mSwipeRefresh.setOnRefreshListener(this);
        initChatUi();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (ivAudio != null) {
                    ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
                    ivAudio = null;
                    MediaManager.reset();
                } else {
                    ivAudio = view.findViewById(R.id.ivAudio);
                    MediaManager.reset();
                    ivAudio.setBackgroundResource(R.drawable.audio_animation_right_list);
                    AnimationDrawable drawable = (AnimationDrawable) ivAudio.getBackground();
                    drawable.start();
                    MediaManager.playSound(ChatActivity.this, ((AudioMsgBody) mAdapter.getData().get(position).getBody()).getLocalPath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            LogUtil.d("开始播放结束");
                            ivAudio.setBackgroundResource(R.mipmap.audio_animation_list_right_3);
                            MediaManager.release();
                        }
                    });
                }
            }
        });

    }

    private void initUserInfoUI() {

    }


    /**
     *
     */
    private void initMessageContent() {
        RelativeLayout common_toolbar = findViewById(R.id.common_toolbar);
        TextView common_toolbar_title = common_toolbar.findViewById(R.id.common_toolbar_title);
        RelativeLayout common_toolbar_back = common_toolbar.findViewById(R.id.common_toolbar_back);
        common_toolbar_back.setOnClickListener(this);
        common_toolbar_title.setText(getIntent().getStringExtra("userNickName"));

        MessageListDBProvider messageListDBProvider = new MessageListDBProvider();
        List<MessageBean> messageBeanList = messageListDBProvider.queryUserMessageByUserId(getIntent().getStringExtra("userId"),20);
//        List<MessageBean> messageBeanList = dbSession.getMessageBeanDao().queryBuilder().limit(10).list();

        List<Message> mReceiveMsgList = new ArrayList<Message>();


        for (MessageBean messageBean : messageBeanList) {
            Message message = new Message();
            MsgType msgType = MsgType.class.getEnumConstants()[messageBean.getMessageType()];
            switch (msgType) {
                case FILE:
                    break;
                case TEXT:
                    message = sysnMessageText(messageBean);
                    break;
                case IMAGE:
                    message = sysnMessageImage(messageBean);
                    break;
            }
            mReceiveMsgList.add(message);

        }
        mAdapter.addData(0, mReceiveMsgList);
        mSwipeRefresh.setRefreshing(false);
    }

    /**
     * 同步文本消息 处理
     */
    private Message sysnMessageText(MessageBean messageBean) {
        UserStateInfo userStateInfo = new UserStateInfo();
        Message mMessgaeText = new Message();
        if (messageBean.getSendUserId().equals(String.valueOf(userStateInfo.getUserId()))) {
//            mMessgaeText = getBaseSendMessage(MsgType.TEXT);
            mMessgaeText.setSenderId(mSenderId);
            mMessgaeText.setTargetId(mTargetId);
        } else {
//            mMessgaeText = getBaseReceiveMessage();
            mMessgaeText.setSenderId(mTargetId);
            mMessgaeText.setTargetId(mSenderId);
        }
        mMessgaeText.setMsgType(MsgType.TEXT);
        mMessgaeText.setSentStatus(MsgSendStatus.SENT);
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(messageBean.getMessageContext());
        mMessgaeText.setBody(mTextMsgBody);
        System.out.println("================mMessgaeText " + messageBean.getSendUserId() + "   " + messageBean.getMessageContext());
        return mMessgaeText;

    }


    /**
     * 同步文本消息 处理
     */
    private Message sysnMessageImage(MessageBean messageBean) {
        Message mMessgaeText = null;
        userStateInfo = new UserStateInfo();
        if (messageBean.getSendUserId().equals(String.valueOf(userStateInfo.getUserId()))) {
            mMessgaeText = getBaseSendMessage(MsgType.IMAGE);
        } else {
            mMessgaeText = getBaseReceiveMessage(MsgType.IMAGE);
        }
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(messageBean.getMessageContext());
        mMessgaeText.setBody(mTextMsgBody);
        mMessgaeText.setSentStatus(MsgSendStatus.SENT);
        System.out.println("================mMessgaeText " + messageBean.getMessageContext());
        return mMessgaeText;

    }

    @Override
    public void onRefresh() {
        //下拉刷新模拟获取历史消息
        List<Message> mReceiveMsgList = new ArrayList<Message>();
        //构建文本消息
        Message mMessgaeText = getBaseReceiveMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage("收到的消息");
        mMessgaeText.setBody(mTextMsgBody);
        mReceiveMsgList.add(mMessgaeText);
        //构建图片消息
        Message mMessgaeImage = getBaseReceiveMessage(MsgType.IMAGE);
        ImageMsgBody mImageMsgBody = new ImageMsgBody();
        mImageMsgBody.setThumbUrl("http://pic19.nipic.com/20120323/9248108_173720311160_2.jpg");
        mMessgaeImage.setBody(mImageMsgBody);
        mReceiveMsgList.add(mMessgaeImage);
        //构建文件消息
        Message mMessgaeFile = getBaseReceiveMessage(MsgType.FILE);
        FileMsgBody mFileMsgBody = new FileMsgBody();
        mFileMsgBody.setDisplayName("收到的文件");
        mFileMsgBody.setSize(12);
        mMessgaeFile.setBody(mFileMsgBody);
        mReceiveMsgList.add(mMessgaeFile);
        mAdapter.addData(0, mReceiveMsgList);
        mSwipeRefresh.setRefreshing(false);
    }


    private void initChatUi() {
        //mBtnAudio
        final ChatUiHelper mUiHelper = ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)
                .bindttToSendButton(mBtnSend)
                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindAddLayout(mLlAdd)
                .bindToAddButton(mIvAdd)
                .bindToEmojiButton(mIvEmo)
                .bindAudioBtn(mBtnAudio)
                .bindAudioIv(mIvAudio)
                .bindEmojiData();
        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
        //
        ((RecordButton) mBtnAudio).setOnFinishedRecordListener(new RecordButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath, int time) {
                LogUtil.d("录音结束回调");
                File file = new File(audioPath);
                if (file.exists()) {
                    sendAudioMessage(audioPath, time);
                }
            }
        });

    }

    @OnClick({R.id.btn_send, R.id.rlPhoto, R.id.rlVideo, R.id.rlLocation, R.id.rlFile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendTextMsg(mEtContent.getText().toString());
//                mEtContent.setText("");
                break;
            case R.id.rlPhoto:
                PictureFileUtil.openGalleryPic(ChatActivity.this, REQUEST_CODE_IMAGE);
                break;
            case R.id.rlVideo:
                PictureFileUtil.openGalleryAudio(ChatActivity.this, REQUEST_CODE_VEDIO);
                break;
            case R.id.rlFile:
                PictureFileUtil.openFile(ChatActivity.this, REQUEST_CODE_FILE);
                break;
            case R.id.rlLocation:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    LogUtil.d("获取到的文件路径:" + filePath);
                    sendFileMessage(mSenderId, mTargetId, filePath);
                    break;
                case REQUEST_CODE_IMAGE:
                    // 图片选择结果回调
                    List<LocalMedia> selectListPic = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListPic) {
                        LogUtil.d("获取图片路径成功:" + media.getPath());
                        sendImageMessage(media);
                    }
                    break;
                case REQUEST_CODE_VEDIO:
                    // 视频选择结果回调
                    List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListVideo) {
                        LogUtil.d("获取视频路径成功:" + media.getPath());
                        sendVedioMessage(media);
                    }
                    break;
            }
        }
    }


    //文本消息
    private void sendTextMsg(String hello) {
        final Message mMessgae = getBaseSendMessage(MsgType.TEXT);
        TextMsgBody mTextMsgBody = new TextMsgBody();
        mTextMsgBody.setMessage(hello);
        mMessgae.setBody(mTextMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);
    }


    //图片消息
    private void sendImageMessage(final LocalMedia media) {
        final Message mMessgae = getBaseSendMessage(MsgType.IMAGE);
        ImageMsgBody mImageMsgBody = new ImageMsgBody();
        mImageMsgBody.setThumbUrl(media.getCompressPath());
        mMessgae.setBody(mImageMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);
    }


    //视频消息
    private void sendVedioMessage(final LocalMedia media) {
        final Message mMessgae = getBaseSendMessage(MsgType.VIDEO);
        //生成缩略图路径
        String vedioPath = media.getPath();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(vedioPath);
        Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime();
        String imgname = System.currentTimeMillis() + ".jpg";
        String urlpath = Environment.getExternalStorageDirectory() + "/" + imgname;
        File f = new File(urlpath);
        try {
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            LogUtil.d("视频缩略图路径获取失败：" + e.toString());
            e.printStackTrace();
        }
        VideoMsgBody mImageMsgBody = new VideoMsgBody();
        mImageMsgBody.setExtra(urlpath);
        mMessgae.setBody(mImageMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);

    }

    //文件消息
    private void sendFileMessage(String from, String to, final String path) {
        final Message mMessgae = getBaseSendMessage(MsgType.FILE);
        FileMsgBody mFileMsgBody = new FileMsgBody();
        mFileMsgBody.setLocalPath(path);
        mFileMsgBody.setDisplayName(FileUtils.getFileName(path));
        mFileMsgBody.setSize(FileUtils.getFileLength(path));
        mMessgae.setBody(mFileMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);

    }

    //语音消息
    private void sendAudioMessage(final String path, int time) {
        final Message mMessgae = getBaseSendMessage(MsgType.AUDIO);
        AudioMsgBody mFileMsgBody = new AudioMsgBody();
        mFileMsgBody.setLocalPath(path);
        mFileMsgBody.setDuration(time);
        mMessgae.setBody(mFileMsgBody);
        //开始发送
        mAdapter.addData(mMessgae);
        //模拟两秒后发送成功
        updateMsg(mMessgae);
    }


    private Message getBaseSendMessage(MsgType msgType) {
        Message mMessgae = new Message();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mSenderId);
        mMessgae.setTargetId(mTargetId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private Message getBaseReceiveMessage(MsgType msgType) {
        Message mMessgae = new Message();
        mMessgae.setUuid(UUID.randomUUID() + "");
        mMessgae.setSenderId(mTargetId);
        mMessgae.setTargetId(mSenderId);
        mMessgae.setSentTime(System.currentTimeMillis());
        mMessgae.setSentStatus(MsgSendStatus.SENDING);
        mMessgae.setMsgType(msgType);
        return mMessgae;
    }


    private void updateMsg(final Message mMessgae) {
        mRvChat.scrollToPosition(mAdapter.getItemCount() - 1);
        //模拟2秒后发送成功
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                int position = 0;
//                mMessgae.setSentStatus(MsgSendStatus.SENT);
//                //更新单个子条目
//                for (int i = 0; i < mAdapter.getData().size(); i++) {
//                    Message mAdapterMessage = mAdapter.getData().get(i);
//                    if (mMessgae.getUuid().equals(mAdapterMessage.getUuid())) {
//                        position = i;
//                    }
//                }
//                mAdapter.notifyItemChanged(position);
//            }
//        }, 2000);
        String sendUserId = userStateInfo.getUserId();
        String targetId = getIntent().getStringExtra("userId");
        TextMsgBody localMsgType = (TextMsgBody) mMessgae.getBody();
        String message = localMsgType.getMessage();
        MsgType msgType = mMessgae.getMsgType();
        int ordinal = msgType.ordinal();
        SendMessageBean sendMessageBean = new SendMessageBean(sendUserId,targetId,message,null,ordinal);
        myBinder.adapterExceptionDispose(EvenBusEnumService.SEND_MESSAGE, sendMessageBean);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpdateSign sendToSever) {
        MessageListDBProvider messageListDBProvider = new MessageListDBProvider();
        List<MessageBean> messageBeanList = messageListDBProvider.queryUpdateMessageByUserId(getIntent().getStringExtra("userId"));
        List<Message> mReceiveMsgList = new ArrayList<Message>();
        for (MessageBean messageBean : messageBeanList) {
            Message message = new Message();
            MsgType msgType = MsgType.class.getEnumConstants()[messageBean.getMessageType()];
            switch (msgType) {
                case FILE:
                    break;
                case TEXT:
                    message = sysnMessageText(messageBean);
                    break;
                case IMAGE:
                    message = sysnMessageImage(messageBean);
                    break;
            }

            mReceiveMsgList.add(message);
        }
        mAdapter.insertData(mReceiveMsgList);
        mLinearLayout.scrollToPosition(mAdapter.getItemCount()-1);
        String targetId = getIntent().getStringExtra("userId");
        messageListDBProvider.updateUserUnreadedMessageNum(targetId);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_toolbar_back:
                Toast.makeText(this, "HHHHH", Toast.LENGTH_SHORT).show();
                mRvChat.scrollToPosition(20);
                break;
        }
    }

    /**
     *   服务调用
     */
    private MyBinder myBinder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyBinder) service;
//            mAdapter.setHideOtherUser();
//            SyncTrendsBean syncTrendsBean = new SyncTrendsBean("",null);
//            myBinder.adapterExceptionDispose(EvenBusEnumService.TRENDS_FLASH, syncTrendsBean);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
