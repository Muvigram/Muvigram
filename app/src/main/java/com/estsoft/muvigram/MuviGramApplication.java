package com.estsoft.muvigram;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.estsoft.muvigram.injection.component.ApplicationComponent;
import com.estsoft.muvigram.injection.component.DaggerApplicationComponent;
import com.estsoft.muvigram.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
public class MuviGramApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        Timber.plant(new Timber.DebugTree());

    }

    public static MuviGramApplication get(Context context) {
        return (MuviGramApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if(mApplicationComponent == null) {

            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

}
