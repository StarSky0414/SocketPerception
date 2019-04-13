package com.tts.starsky.apperceive.manager;

import com.tts.starsky.apperceive.bean.UserStateInfo;
import com.tts.starsky.apperceive.bean.service.SyncMessageRequestBean;
import com.tts.starsky.apperceive.localserver.LocalServicTcpRequestManage;
import com.tts.starsky.apperceive.service.EvenBusEnumService;
import java.util.Timer;
import java.util.TimerTask;

/**
 *  消息更新类  用于主动同步以及监听通信
 */
public class MessageServiceManager {

    private static Timer timer;

    /**
     *  消息更新任务类
     */
    static class  MessageUpdateTask extends TimerTask {
        @Override
        public void run() {
            String userId = UserStateInfo.getUserId();
            String userClientMessageId = UserStateInfo.getUserClientMessageId();
            SyncMessageRequestBean syncMessageRequestBean = new SyncMessageRequestBean(userId, userClientMessageId);
            System.out.println("syncTrendsBean: ============= " + syncMessageRequestBean.toString());
            LocalServicTcpRequestManage.execLocalServic(EvenBusEnumService.SYNC_MESSAGE, syncMessageRequestBean);
        }
    }

    /**
     * 开启消息更新任务
     * @param second  要间隔更新的秒数
     */
    public static void messageUpdateTaskInit(int second){
        //创建一个定时器对象
        timer = new Timer();
        TimerTask task = new MessageUpdateTask();        //创建定时器任务对象，必须实现run方法，在该方法中定义用户任务
        timer.schedule(task, 0, second*1000);
        task.run();
    }

    /**
     *  关闭任务
     */
    public static void stopTask(){
        if (timer != null){
            timer.cancel();
        }
    }
}
