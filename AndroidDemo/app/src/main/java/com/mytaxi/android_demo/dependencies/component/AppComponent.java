package com.mytaxi.android_demo.dependencies.component;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.dependencies.module.NetworkModule;
import com.mytaxi.android_demo.dependencies.module.PermissionModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, PermissionModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(AuthenticationActivity activity);

}
