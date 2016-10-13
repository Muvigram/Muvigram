package com.estsoft.muvigram.ui.intro;

import android.net.Uri;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by gangGongUi on 2016. 10. 11..
 */
public interface IntroView extends MvpView {
    void playIntroView(Uri videoFile);
}
