package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.utils.PermissionHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PermissionModule {

    @Singleton
    @Provides
    PermissionHelper providePermissionHelper() {
        return new PermissionHelper();
    }

}
