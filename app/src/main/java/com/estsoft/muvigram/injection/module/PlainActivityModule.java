package com.estsoft.muvigram.injection.module;

import android.app.Activity;
import android.content.Context;

import com.estsoft.muvigram.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Module
public class PlainActivityModule {

    private Activity mActivity;

    /** Constructor */
    public PlainActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    /* System */
    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    /* Functionality */
    // TODO - Functional dependency objects would be here.
}
