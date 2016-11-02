package com.estsoft.muvigram.injection.module;

import android.app.Activity;
import android.content.Context;

import com.estsoft.muvigram.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaylim on 10/31/2016.
 */

@Module
public class SingleFragmentActivityModule {

    private Activity mActivity;

    /** Constructor */
    public SingleFragmentActivityModule(Activity activity) {
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
