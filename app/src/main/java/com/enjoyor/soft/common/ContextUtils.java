package com.enjoyor.soft.common;

import android.app.Application;

/**
 * Created by 83916 on 2015/12/10.
 */
public class ContextUtils extends Application{
    private ContextUtils instance;

    public ContextUtils getInstance(){
        if (instance==null){
            instance = new ContextUtils();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
