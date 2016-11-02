package com.estsoft.muvigram.ui.musicselect.online;

import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.base.Presenter;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.PerNestedFragment;

import javax.inject.Inject;

/**
 * Created by jaylim on 11/1/2016.
 */


@PerNestedFragment
public class MusicSelectOnlinePresenter extends BasePresenter<MusicSelectView.OnlineView> {

    @Inject
    public MusicSelectOnlinePresenter() {
    }
}
