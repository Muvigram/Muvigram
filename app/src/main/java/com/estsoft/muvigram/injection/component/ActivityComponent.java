package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.PerActivity;
import com.estsoft.muvigram.injection.module.ActivityModule;
import com.estsoft.muvigram.ui.camera.CameraActivity;
import com.estsoft.muvigram.ui.feed.comment.CommentActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.intro.IntroActivity;
import com.estsoft.muvigram.ui.profile.ProfileFragment;
import com.estsoft.muvigram.ui.selectmusic.MusicSelectActivity;
import com.estsoft.muvigram.ui.sign.SignInActivity;
import com.estsoft.muvigram.ui.splash.SplashActivity;
import com.estsoft.muvigram.ui.videoselect.VideoSelectActivity;

import dagger.Subcomponent;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(SplashActivity activity);

    void inject(HomeActivity activity);

    void inject(IntroActivity activity);

    void inject(MusicSelectActivity musicSelectActivity);

    void inject(CameraActivity cameraActivity);

    void inject(VideoSelectActivity videoSelectActivity);

    void inject(SignInActivity signInActivity);

    void inject(ProfileFragment profileFragment);

    void inject(CommentActivity commentActivity);
}
