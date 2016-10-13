package com.estsoft.muvigram.injection.module;

import android.app.Application;
import android.content.Context;

import com.estsoft.muvigram.data.remote.LoginSignService;
import com.estsoft.muvigram.data.remote.NetWorkTestService;
import com.estsoft.muvigram.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    NetWorkTestService providesNetWorkTestService()  { return new NetWorkTestService(); }

    @Provides
    @Singleton
    LoginSignService provideRibotsService() { return LoginSignService.Creator.newLoginSignService(); }

}
