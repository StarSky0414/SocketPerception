package com.tts.starsky.apperceive.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.db.bao.DaoMaster;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bao.UserBeanDao;
import com.tts.starsky.apperceive.db.bao.UserSelfBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageBean;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.bean.UserBean;
import com.tts.starsky.apperceive.db.bean.UserSelfBean;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

public class DBTest {

    public void creat(Context context) {
        //获取DevOpenHelper   它继承 SQLiteOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "my.db");
//获取读写数据库  需要注意的是
        Database db = devOpenHelper.getWritableDb();
//获取  数据库session
        DaoSession daoSession = new DaoMaster(db).newSession();
//获取操作UserBean 的类
//        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
//        userBeanDao.insert(new UserBean(null, "张三", "123"));
//
        MessageBeanDao messageBeanDao = daoSession.getMessageBeanDao();
        messageBeanDao.insert(new MessageBean(null, "1","2","1", "反方向数据", "11:50","", 0));
/*        messageBeanDao.insert(new MessageBean(null, "2","1","1", "数据库测试数据", "11:20","", 1));
        messageBeanDao.insert(new MessageBean(null, "2","1","2", "数据库测试数据", "11:30","", 0));
        messageBeanDao.insert(new MessageBean(null, "2","1", "2","数据库测试数据", "11:20","", 0));
        messageBeanDao.insert(new MessageBean(null, "2","1", "1","数据库测试数据", "11:40","", 0));*/

        long count = messageBeanDao.queryBuilder().count();
        System.out.println("=================== messageBeanDao.queryBuilder(): "+count);

        List<MessageBean> list = messageBeanDao.queryBuilder().list();
        for (MessageBean messageBean : list) {
            System.out.println("=================messageBean : "+messageBean.toString());
        }

//        UserSelfBeanDao userSelfBeanDao = daoSession.getUserSelfBeanDao();
//        userSelfBeanDao.insert(new UserSelfBean(2L,"aaaa", R.mipmap.ic_launcher_round));
//
//        UserBeanDao userBeanDao = daoSession.getUserBeanDao();
//        userBeanDao.insert(new UserBean(Long.valueOf(1),"这是大哥", R.mipmap.ic_launcher_round,0));
//        userBeanDao.insert(new UserBean(Long.valueOf(2), "这是大哥2", R.mipmap.ic_launcher_round,0));
////        //方法一

//        daoSession.getUserSelfBeanDao()
//        List<MessageBean> mDayStep = messageBeanDao.loadAll();
//        //方法二
//        //List<dayStep> mDayStep = dao.queryBuilder().list();
//        //方法三 惰性加载
//        //List<dayStep> mDayStep = dao.queryBuilder().listLazy();
//        for (int i = 0; i < mDayStep.size(); i++) {
//            String date = "";
//            date = mDayStep.get(i).toString();
//            Log.d("======================", "id:  " + i + "date:  " + date);
//        }
    }
}
