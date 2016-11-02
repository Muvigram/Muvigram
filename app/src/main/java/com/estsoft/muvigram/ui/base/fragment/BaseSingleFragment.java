package com.estsoft.muvigram.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.injection.component.SingleFragmentComponent;
import com.estsoft.muvigram.injection.component.SingleFragmentActivityComponent;
import com.estsoft.muvigram.injection.module.SingleFragmentModule;

/**
 *
 * Created by jaylim on 10/31/2016.
 */

public class BaseSingleFragment extends Fragment {

    private SingleFragmentComponent mSingleFragmentComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSingleFragmentComponent = getSingleFragmentActivityComponent().plus(new SingleFragmentModule());
    }

    public SingleFragmentComponent getSingleFragmentComponent() {
        return mSingleFragmentComponent;
    }

    /** Do not use in any fragment but only in {@link BaseSingleFragment} */
    private SingleFragmentActivityComponent getSingleFragmentActivityComponent() {
        return BaseSingleFragmentActivity.get(this).getSingleFragmentActivityComponent();
    }
}
