package com.zackmatthews.rxjava.presenters;

import com.zackmatthews.rxjava.contracts.MainContract;
import com.zackmatthews.rxjava.models.DbObject;
import com.zackmatthews.rxjava.models.LpApi;

import rx.Subscriber;

/**
 * Created by zmatthews on 2/14/18.
 */

public class MainPresenter implements MainContract.Presenter{
    public enum ACTION{
        DISPLAY_OBJ
    }

    @Override
    public void createObject(DbObject obj) {
        LpApi.getInstance().createObject(obj);
    }

    @Override
    public void getObject(String id, final ACTION action, final LpApi.ObjectAccessCallback callback) {
        LpApi.getInstance().getObjById(id, new Subscriber<DbObject>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                try {
                    if (callback != null) {
                        callback.onObjectRetrieved(null, action);
                    }
                    if (!isUnsubscribed()) {
                        unsubscribe();
                    }
                }
                catch(Throwable err){
                    err.printStackTrace();
                }
            }

            @Override
            public void onNext(DbObject dbObject) {
                if(callback != null) {
                    callback.onObjectRetrieved(dbObject, action);
                }
                if(!isUnsubscribed()) {
                    unsubscribe();
                }
            }
        });
    }
}
