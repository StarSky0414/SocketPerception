package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.MainActivity;
import com.tts.starsky.apperceive.bean.evenbus.callbackbean.MessageUpdateSign;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivityManager {

    private MainActivity mainActivity;

    public MainActivityManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    /**
     *  EvenBus 更新消息时候进行通知
     * @param sendToSever 更新消息标记
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpdateSign sendToSever) {
        DaoSession dbSession = DBBase.getDBBase().getDBSession();
        long count = dbSession.getMessageBeanDao().queryBuilder().where(MessageBeanDao.Properties.Readed.eq(0)).count();
        mainActivity.changeButtonNum(count);
    }

}
