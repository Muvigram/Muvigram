package com.estsoft.muvigram.injection.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.estsoft.muvigram.injection.qualifier.NestedFragment;
import com.estsoft.muvigram.ui.base.nestedfragment.BaseParentFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jaylim on 11/2/2016.
 */

@Module
public class ParentFragmentModule {

    private Fragment mFragment;

    /** Constructor */
    public ParentFragmentModule(Fragment fragment) {
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
