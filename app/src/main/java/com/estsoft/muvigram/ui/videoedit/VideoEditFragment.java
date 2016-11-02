package com.estsoft.muvigram.ui.videoedit;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditFragment extends BaseSingleFragment implements VideoEditView {

    @Inject VideoEditPresenter mPresenter;

    public static VideoEditFragment newInstance() {
        return new VideoEditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    /* View logic here ... */
}
