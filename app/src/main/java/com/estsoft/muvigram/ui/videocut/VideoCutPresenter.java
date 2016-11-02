package com.estsoft.muvigram.ui.videocut;

import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoCutPresenter extends BasePresenter<VideoCutView> {

    @Inject
    public VideoCutPresenter() {
    }

    @Override
    public void attachView(VideoCutView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here ... */
}
