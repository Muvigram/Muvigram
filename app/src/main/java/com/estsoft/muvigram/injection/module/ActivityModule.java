package com.estsoft.muvigram.injection.module;

import android.app.Activity;
import android.content.Context;

import com.estsoft.muvigram.data.remote.NetWorkTestService;
import com.estsoft.muvigram.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }


}
