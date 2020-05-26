package com.aylong.myapplication;

import android.util.Log;

import javax.inject.Inject;

/**
 * Create by Aylong
 * Date  2020/5/19
 **/
public class Person implements IPerson {
    @Inject
    public Person(){
        Log.d("JAY_TEST", "Person:this is a person ");
    }

}
