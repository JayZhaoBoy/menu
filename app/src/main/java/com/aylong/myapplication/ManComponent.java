package com.aylong.myapplication;

import dagger.Component;

/**
 * Create by Aylong
 * Date  2020/5/19
 **/
@Component(modules = ManModule.class)
public interface ManComponent {
    void inject(MainActivity mainActivity);
}
