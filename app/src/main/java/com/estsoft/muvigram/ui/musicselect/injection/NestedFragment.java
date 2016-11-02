package com.estsoft.muvigram.ui.musicselect.injection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.injection.component.SingleFragmentComponent;
import com.estsoft.muvigram.ui.musicselect.injection.component.NestedFragmentComponent;
import com.estsoft.muvigram.ui.musicselect.injection.module.NestedFragmentModule;
import com.estsoft.muvigram.ui.musicselect.pager.MusicSelectFragment;

/**
 * Created by jaylim on 11/2/2016.
 */

public class NestedFragment extends Fragment {

    private NestedFragmentComponent mNestedFragmentComponent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNestedFragmentComponent = getSingleFragmentComponent().plus(new NestedFragmentModule());
    }

    private SingleFragmentComponent getSingleFragmentComponent() {
        return MusicSelectFragment.get(this).getSingleFragmentComponent();
    }


    public NestedFragmentComponent getNestedFragmentComponent() {
        return mNestedFragmentComponent;
    }
}
