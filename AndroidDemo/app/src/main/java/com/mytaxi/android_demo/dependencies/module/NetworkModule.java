package com.mytaxi.android_demo.dependencies.module;

import com.mytaxi.android_demo.utils.network.HttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    HttpClient provideHttpClient() {
        return new HttpClient();
    }

}
