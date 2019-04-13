package com.tts.starsky.apperceive;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.tts.starsky.apperceive.db.BaseBDTest;
import com.tts.starsky.apperceive.db.DBBase;
import com.tts.starsky.apperceive.db.bao.DaoSession;
import com.tts.starsky.apperceive.db.bao.MessageBeanDao;
import com.tts.starsky.apperceive.db.bean.MessageListBean;
import com.tts.starsky.apperceive.db.provider.MessageListDBProvider;
import com.tts.starsky.apperceive.exception.DBException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends BaseBDTest {
//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.tts.starsky.apperceive", appContext.getPackageName());
//    }

    private static Context context = InstrumentationRegistry.getTargetContext();
    public ExampleInstrumentedTest() {
        super(context);
    }




    @Test
    public void querDBTableName() throws DBException {
        DaoSession daoSession = init();
        MessageListDBProvider messageListDBProvider = new MessageListDBProvider(daoSession);
        List<MessageListBean> messageListBeans = messageListDBProvider.queryMessageList();
        int size = messageListBeans.size();
        assertEquals(size,1);
    }
}
