package com.zackmatthews.rxjava.models;

import com.zackmatthews.rxjava.presenters.MainPresenter;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.schedulers.NewThreadScheduler;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;

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
        Observable.create(new AsyncOnSubscribe<Object, Object>() {
            @Override
            protected void onUnsubscribe(Object state) {
                super.onUnsubscribe(state);
            }

            @Override
            protected Object generateState() {
                return null;
            }

            @Override
            protected Object next(Object state, long requested, Observer<Observable<?>> observer) {
                MockServer.addObject(obj);
                return null;
            }
        }).subscribe();

    }
    public Subscription getObjById(final String id, final Subscriber<? super DbObject> subscriber){
       Observable observable = Observable.create(new AsyncOnSubscribe<DbObject, Object>() {
            @Override
            protected void onUnsubscribe(DbObject state) {
                super.onUnsubscribe(state);
            }

            @Override
            protected DbObject generateState() {
                return MockServer.getObjectForId(id);
            }

            @Override
            protected DbObject next(DbObject state, long requested, Observer<Observable<?>> observer) {
                subscriber.onNext(state);
                return state;
            }
        }).observeOn(Schedulers.newThread());
       return observable.subscribe(subscriber);
    }
}
