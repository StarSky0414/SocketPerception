package com.tts.starsky.apperceive.db;

import android.content.Context;

import com.tts.starsky.apperceive.db.bao.DaoMaster;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.exception.DBException;

import org.greenrobot.greendao.database.Database;

public class DBBase {

    private static Context context;
    //数据库名
    private static final String DBNAME="my.db";
    //DBBase对象
    private static DBBase dbBase;

    private DBBase() {
    }

    /**
     * 初始化数据库
     * @param context 获取context
     */
    public static void dbBaseinit(Context context){
        DBBase.context = context;
        dbBase=new DBBase();
    }

    /**
     * 获取DBBase对象
     * @return DBBase对象
     * @throws DBException 数据库使用未初始化
     */
    public static DBBase getDBBase() throws DBException {
        if (dbBase==null){
            throw new DBException(DBException.UNINITEXCEPTION);
        }
        return dbBase;
    }

    /**
     * 获取数据库session
     * @return 数据库session
     */
    public DaoSession getDBSession(){
        //获取DevOpenHelper   它继承 SQLiteOpenHelper
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DBNAME);
        Database db = devOpenHelper.getWritableDb();
        //获取  数据库session
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession;
    }
}
