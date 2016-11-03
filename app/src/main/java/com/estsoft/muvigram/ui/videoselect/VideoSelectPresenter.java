package com.estsoft.muvigram.ui.videoselect;

import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoSelectPresenter extends BasePresenter<VideoSelectView> {

    @Inject
    public VideoSelectPresenter() {
    }

    @Override
    public void attachView(VideoSelectView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here ... */
}
