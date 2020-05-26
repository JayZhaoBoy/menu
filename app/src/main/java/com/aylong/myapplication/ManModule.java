package com.aylong.myapplication;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Create by Aylong
 * Date  2020/5/20
 **/
@Module
public abstract class ManModule {
    @Binds
    abstract IPerson bindPerson(Person person);
}
