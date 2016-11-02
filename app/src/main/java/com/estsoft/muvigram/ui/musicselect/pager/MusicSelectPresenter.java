package com.estsoft.muvigram.ui.musicselect.pager;

import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.injection.qualifier.ParentFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerParentFragment
public class MusicSelectPresenter extends BasePresenter<MusicSelectView.ParentView> {

    @Inject
    public MusicSelectPresenter() {
    }

    @Override
    public void attachView(MusicSelectView.ParentView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here... */

}
