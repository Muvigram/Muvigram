package com.estsoft.muvigram.ui.intro;

import android.content.Context;
import android.net.Uri;

import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by gangGongUi on 2016. 10. 11..
 */
@ConfigPersistent
public class IntroPresenter extends BasePresenter<IntroView> {

    @Inject
    public IntroPresenter() {
    }

    @Override
    public void attachView(IntroView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadVideo() {
        final String VIDEO_FILE_NAME = "test_intro_video";
        final Context context = (Context) getMvpView();
        final Uri videoFile = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + VIDEO_FILE_NAME);
        getMvpView().playIntroView(videoFile);
    }
}
