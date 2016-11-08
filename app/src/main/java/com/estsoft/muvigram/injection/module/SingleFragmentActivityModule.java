package com.estsoft.muvigram.injection.module;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;

import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.injection.qualifier.ParentFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

import javax.inject.Singleton;

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

    @Provides
    @ParentFragment
    FragmentManager providesFragmentManager() {
        return ((BaseSingleFragmentActivity) mActivity).getSupportFragmentManager();
    }

    /* Functionality */
    // TODO - Functional dependency objects would be here.
//    @Provides @Singleton
//    MediaPlayer providesMediaPlayer() {
//        return new MediaPlayer();
//    }
}
