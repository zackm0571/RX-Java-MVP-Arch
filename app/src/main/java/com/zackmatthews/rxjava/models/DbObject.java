package com.zackmatthews.rxjava.models;

/**
 * Created by zmatthews on 2/14/18.
 */

public class DbObject {
    private String id, name;

    public DbObject(String id, String name){
        setId(id);
        setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
