package com.tts.starsky.apperceive.db;

import android.content.Context;

import com.tts.starsky.apperceive.db.bao.DaoMaster;
import com.tts.starsky.apperceive.db.bao.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.junit.Before;

public class BaseBDTest {


    //数据库名
    private static final String DBNAME="my.db";
    private Context context;

    protected BaseBDTest(Context context){
        this.context=context;
    }
    public BaseBDTest() {
    }

    public DaoSession init(){
        //获取DevOpenHelper   它继承 SQLiteOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,DBNAME );
        Database db = devOpenHelper.getWritableDb();
        //获取  数据库session
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession;
    }
}
