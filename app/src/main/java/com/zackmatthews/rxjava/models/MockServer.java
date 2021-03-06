package com.zackmatthews.rxjava.models;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by zmatthews on 2/14/18.
 */

public class MockServer {
    private static int count = 0;
    private static HashMap<String, DbObject> objects = new HashMap<>();

    public static int getObjCount(){

        return count;
    }
    public static void addObject(DbObject obj) {
        objects.put(obj.getId(), obj);
        count++;
    }
    @Nullable
    public static DbObject getObjectForId(String id){
        try{
            Thread.sleep(2000 + new Random().nextInt(3000));
        }
        catch(Throwable e){
            e.printStackTrace();
        }
        return objects.get(id);
    }
}
