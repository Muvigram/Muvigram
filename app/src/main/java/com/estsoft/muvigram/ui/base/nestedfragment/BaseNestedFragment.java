package com.estsoft.muvigram.ui.base.nestedfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.injection.component.NestedFragmentComponent;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.injection.module.NestedFragmentModule;

/**
 * Created by jaylim on 11/2/2016.
 */

public class BaseNestedFragment extends Fragment {

    private NestedFragmentComponent mNestedFragmentComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNestedFragmentComponent = getParentFragmentComponent().plus(new NestedFragmentModule());
    }

    public NestedFragmentComponent getNestedFragmentComponent() {
        return mNestedFragmentComponent;
    }

    private ParentFragmentComponent getParentFragmentComponent() {
        return BaseParentFragment.get(this).getParentFragmentComponent();
    }

}
