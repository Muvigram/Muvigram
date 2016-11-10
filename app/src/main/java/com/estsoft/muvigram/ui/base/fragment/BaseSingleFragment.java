package com.estsoft.muvigram.ui.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.injection.component.SingleFragmentComponent;
import com.estsoft.muvigram.injection.module.SingleFragmentModule;

/**
 *
 * Created by jaylim on 10/31/2016.
 */

public class BaseSingleFragment extends BaseFragment {

    private SingleFragmentComponent mSingleFragmentComponent;


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSingleFragmentComponent = getSingleFragmentActivityComponent().plus(new SingleFragmentModule(this));
    }

    public SingleFragmentComponent getSingleFragmentComponent() {
        return mSingleFragmentComponent;
    }

}
