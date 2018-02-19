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
    private MainContract.View view;

    public MainPresenter(MainContract.View view){
        this.view = view;
    }
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
                if(view != null) {
                    view.toggleProgressBar(true);
                }
            }

            @Override
            public void onNext(DbObject dbObject) {
                if(callback != null) {
                    callback.onObjectRetrieved(dbObject, action);
                }

                if(view != null) {
                    view.toggleProgressBar(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                if(view != null) {
                    view.toggleProgressBar(false);
                }
            }

            @Override

            public void onComplete() {
                if(view != null) {
                    view.toggleProgressBar(false);
                }
            }
        });
    }
}
