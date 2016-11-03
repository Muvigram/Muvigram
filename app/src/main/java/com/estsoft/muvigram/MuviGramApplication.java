package com.estsoft.muvigram;

import android.app.Application;
import android.content.Context;

import com.estsoft.muvigram.injection.component.ApplicationComponent;
import com.estsoft.muvigram.injection.component.DaggerApplicationComponent;
import com.estsoft.muvigram.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
public class MuvigramApplication extends Application {

    ApplicationComponent mApplicationComponent;

    public static MuvigramApplication get(Context context) {
        return (MuvigramApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {

            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }



}
