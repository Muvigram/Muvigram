package com.estsoft.muvigram.injection.component;

import android.app.Application;
import android.content.Context;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.data.remote.NetWorkTestService;
import com.estsoft.muvigram.injection.ApplicationContext;
import com.estsoft.muvigram.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
}
