package com.estsoft.muvigram.ui.videocut;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoCutFragment extends BaseSingleFragment implements VideoCutView {

    @Inject VideoCutPresenter mPresenter;

    public static VideoCutFragment newInstance() {
        return new VideoCutFragment();
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

    /* View logic implementation here ... */
}
