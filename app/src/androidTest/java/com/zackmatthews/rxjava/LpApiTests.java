package com.zackmatthews.rxjava;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.zackmatthews.rxjava.models.DbObject;
import com.zackmatthews.rxjava.models.LpApi;
import com.zackmatthews.rxjava.models.MockServer;
import com.zackmatthews.rxjava.presenters.MainPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LpApiTests implements LpApi.ObjectAccessCallback{
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zackmatthews.rxjava", appContext.getPackageName());
    }

    @Test
    public void createObject() throws Exception{
        LpApi.getInstance().createObject(new DbObject("1", "obj1"));
        assertTrue(MockServer.getObjCount() > 0);
        assertEquals(MockServer.getObjectForId("1").getName(), "obj1");
    }

    @Test public void getObject() throws Exception{
        MainPresenter presenter = new MainPresenter();
        presenter.createObject(new DbObject("1", "obj1"));
        presenter.getObject("1", MainPresenter.ACTION.DISPLAY_OBJ, this);
        Thread.sleep(3000);
    }

    @Override
    public void onObjectRetrieved(DbObject obj, MainPresenter.ACTION action) {
        assertTrue(obj != null);
    }
}
