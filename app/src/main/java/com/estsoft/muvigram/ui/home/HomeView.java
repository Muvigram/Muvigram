package com.estsoft.muvigram.ui.home;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
public interface HomeView extends MvpView {

    void showNetworkError();

    void showTestToast(int i);
}
