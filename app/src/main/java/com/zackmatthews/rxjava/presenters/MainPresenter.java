package com.zackmatthews.rxjava.presenters;

import com.zackmatthews.rxjava.contracts.MainContract;
import com.zackmatthews.rxjava.models.DbObject;
import com.zackmatthews.rxjava.models.LpApi;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
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
        LpApi.getInstance().getObjById(id).subscribeWith(new Observer<DbObject>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(DbObject dbObject) {
                if(callback != null) {
                    callback.onObjectRetrieved(dbObject, action);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
