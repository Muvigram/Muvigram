package com.estsoft.muvigram.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.injection.qualifier.ParentFragment;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

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

    @Provides
    @ParentFragment
    FragmentManager providesFragmentManager() {
        return ((BasePlainActivity) mActivity).getSupportFragmentManager();
    }

    /* Functionality */
    // TODO - Functional dependency objects would be here.
}
