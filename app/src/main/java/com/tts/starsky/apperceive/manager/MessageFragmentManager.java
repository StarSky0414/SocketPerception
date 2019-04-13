package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.SycnMessageSucc;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.controller.adapter.MessageListAdapter;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.provider.MessageListDBProvider;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import com.tts.starsky.apperceive.view.fragment.MessageFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 *  消息列表 Manager
 */
public class MessageFragmentManager {
    // 页面消息List 列表
    private static List<MessageListBean> messageList=new ArrayList<>();
    private MessageListAdapter mAdapter;
    private MessageFragment messageFragment;

    public MessageFragmentManager(MessageFragment messageFragment) {
        EventBus.getDefault().register(this);
        mAdapter = new MessageListAdapter(messageFragment.getContext(),messageList);
        this.messageFragment = messageFragment;
    }

    public static void DataInit(){
        String userId = UserStateInfo.getUserId();
        String userClientMessageId = UserStateInfo.getUserClientMessageId();
        SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userClientMessageId);
        System.out.println("syncTrendsBean: ============= " + syncMessageRequestBean.toString());
        LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE,syncMessageRequestBean);
        messageList = new MessageListDBProvider().queryMessageList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpdateSign sendToSever) {
        DaoSession dbSession = DBBase.getDBBase().getDBSession();
        long count = dbSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.Readed.eq(0)).count();
        updateMessageList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestMessage(SycnMessageSucc sycnMessageSucc){
       messageFragment.updateViewFlush();
    }

//    public void Event(UpdateMessageListBean updateMessageListBean) {
//        messageList = updateMessageListBean.getMessageListBeanList();
//        mAdapter.changeData(messageList);
//        Log.w("========", "run Trends");
//    }

    public void updateMessageList() {
        messageList = new MessageListDBProvider().queryMessageList();
        mAdapter.changeData(messageList);

    }

    public static List<MessageListBean> getMessageList() {
        return messageList;
    }

    public static void setMessageList(List<MessageListBean> messageList) {
        MessageFragmentManager.messageList = messageList;
    }

    public MessageListAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(MessageListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }
}
