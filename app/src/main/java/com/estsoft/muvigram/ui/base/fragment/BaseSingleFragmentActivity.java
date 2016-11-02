package com.estsoft.muvigram.ui.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.component.SingleFragmentActivityComponent;
import com.estsoft.muvigram.injection.module.SingleFragmentActivityModule;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;

/**
 *
 * Created by jaylim on 10/31/2016.
 */
public abstract class BaseSingleFragmentActivity extends BaseActivity {

    private SingleFragmentActivityComponent mSingleFragmentActivityComponent;

    public static BaseSingleFragmentActivity get(BaseSingleFragment baseSingleFragment) {
        return (BaseSingleFragmentActivity) baseSingleFragment.getActivity();
    }

    protected abstract Fragment createDefaultFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate activity fragment layout
        setContentView(R.layout.activity_single_fragment);

        // Get fragment manager and find fragment view object
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createDefaultFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        mSingleFragmentActivityComponent = getConfigPersistentComponent()
                .plus(new SingleFragmentActivityModule(this));
    }

    public SingleFragmentActivityComponent getSingleFragmentActivityComponent() {
        return mSingleFragmentActivityComponent;
    }
}
