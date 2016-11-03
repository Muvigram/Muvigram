package com.estsoft.muvigram.injection.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.estsoft.muvigram.injection.qualifier.NestedFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaylim on 10/31/2016.
 */

@Module
public class SingleFragmentModule {

    private Fragment mFragment;

    /** Constructor */
    public SingleFragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @NestedFragment
    FragmentManager provideChildFragmentManager() {
        return mFragment.getChildFragmentManager();
    }

}
