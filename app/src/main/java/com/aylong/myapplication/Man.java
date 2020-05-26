package com.aylong.myapplication;

import android.util.Log;

import javax.inject.Inject;

/**
 * Create by Aylong
 * Date  2020/5/19
 **/
public class Man {
    @Inject
    public Man(IPerson person){
        Log.d("JAY_TEST", "Man: this is a man");
    }

    public void say(){
        Log.d("JAY_TEST", "say: i'm a Man");
    }
}
