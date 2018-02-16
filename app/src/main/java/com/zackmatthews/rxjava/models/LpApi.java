package com.zackmatthews.rxjava.models;

import com.zackmatthews.rxjava.presenters.MainPresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.observables.AsyncOnSubscribe;

/**
 * Created by zmatthews on 2/14/18.
 */

public class LpApi {

    private static LpApi instance;
    private Observable<DbObject> observable;

    public interface ObjectAccessCallback{
        void onObjectRetrieved(DbObject obj, MainPresenter.ACTION action);
    }

    public static LpApi getInstance() {
        if(instance == null){
            instance = new LpApi();
        }
        return instance;
    }


    public void createObject(final DbObject obj){
        Observable.create(new ObservableOnSubscribe<DbObject>() {
            @Override
            public void subscribe(ObservableEmitter<DbObject> emitter) throws Exception {
                MockServer.addObject(obj);
                emitter.onNext(obj);
            }
        }).subscribe();
    }


    public Observable getObjById(final String id){

        Observable<DbObject> observable = Observable.create(new ObservableOnSubscribe<DbObject>() {

            @Override
            public void subscribe(ObservableEmitter<DbObject> emitter) throws Exception {
                emitter.onNext(MockServer.getObjectForId(id));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


       return observable;
    }
}
