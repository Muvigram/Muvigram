package com.estsoft.muvigram.ui.base.nestedfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.injection.component.SingleFragmentActivityComponent;
import com.estsoft.muvigram.injection.module.ParentFragmentModule;
import com.estsoft.muvigram.ui.base.fragment.BaseFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 *
 * Created by jaylim on 11/2/2016.
 */

public class BaseParentFragment extends BaseFragment {

    private ParentFragmentComponent mParentFragmentComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParentFragmentComponent = getSingleFragmentActivityComponent().plus(new ParentFragmentModule(this));
    }

    public ParentFragmentComponent getParentFragmentComponent() {
        return mParentFragmentComponent;
    }

    public static BaseParentFragment get(BaseNestedFragment baseNestedFragmente) {
        return (BaseParentFragment) baseNestedFragmente.getParentFragment();
    }
}
