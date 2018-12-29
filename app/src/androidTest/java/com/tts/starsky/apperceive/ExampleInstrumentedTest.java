package com.tts.starsky.apperceive;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.exception.DBException;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.tts.starsky.apperceive", appContext.getPackageName());
    }

    @Test
    public void querDBTableName() throws DBException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBBase.dbBaseinit(appContext);
        DBBase dbBase = DBBase.getDBBase();
        DaoSession dbSession = dbBase.getDBSession();
//        dbSession.getChatMessageDao();
        ;
//        String sql= " select * form "+MessageBeanDao.TABLENAME+"where messageId";
//
//        dbSession.getDatabase().rawQuery()
    }
}
