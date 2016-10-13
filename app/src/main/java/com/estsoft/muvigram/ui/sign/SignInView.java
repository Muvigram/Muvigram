package com.estsoft.muvigram.ui.sign;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by gangGongUi on 2016. 10. 11..
 */
public interface SignInView extends MvpView {
    void enableSignIn();
    void disableSignIn();
    void hidePasswordError();
    void showPasswordError();
    void hideEmailError();
    void showEmailError();
}
