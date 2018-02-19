package com.zackmatthews.rxjava.contracts;

import com.zackmatthews.rxjava.models.DbObject;
import com.zackmatthews.rxjava.models.LpApi;
import com.zackmatthews.rxjava.presenters.MainPresenter;

/**
 * Created by zmatthews on 2/14/18.
 */

public class MainContract {

    public interface View {
        void showObject(DbObject obj);
        void toggleProgressBar(boolean isVisible);

    }

    public interface Presenter{
        void createObject(DbObject obj);
        void getObject(String id, MainPresenter.ACTION action,LpApi.ObjectAccessCallback callback);
    }
}
