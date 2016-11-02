package com.estsoft.muvigram.ui.videoedit;

import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoEditPresenter extends BasePresenter<VideoEditView> {

    @Inject
    public VideoEditPresenter() {
    }

    @Override
    public void attachView(VideoEditView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here ... */
}
