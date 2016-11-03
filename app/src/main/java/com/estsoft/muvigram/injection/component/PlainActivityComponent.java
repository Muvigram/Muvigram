package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.injection.module.PlainActivityModule;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.camera.CameraActivity;
import com.estsoft.muvigram.ui.feed.comment.CommentActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.intro.IntroActivity;
import com.estsoft.muvigram.ui.musicselect.MusicSelectActivity;
import com.estsoft.muvigram.ui.profile.ProfileFragment;
import com.estsoft.muvigram.ui.sign.SignInActivity;
import com.estsoft.muvigram.ui.splash.SplashActivity;
import com.estsoft.muvigram.ui.videoselect.VideoSelectActivity;

import dagger.Subcomponent;

/**
 * Typical usage of the activity component.
 *
 * In android's perspective :
 *   1. It has the scope named {@link PerPlainActivity}
 *      whose life is bound to the lifecycle of the activity.
 *   2. It is responsible for maintaining the objects
 *      that should be kept up along an activity.
 *   3. It is recommended to inject general-for-activity functionality here.
 *      If you need to inject specific dependency for an specific activity,
 *      you have two choices
 *     1) Use Constructor Injection
 *     2) Make new component that has more specific scope
 *
 * In dagger's perspective :
 *   1. This component has scalability for extension to specify activity component.
 *
 * In DI's perspective :
 *   1.
 *   2.
 *   3.
 *
 * Created  by gangGongUi on 2016. 10. 9.
 * Modified by Jay Lim    on 2016. 10. 31.
 */
@PerPlainActivity
@Subcomponent(modules = PlainActivityModule.class)
public interface PlainActivityComponent {

    /* Subcomponent */

    /* Dependencies extended by constructor injections */
    // TODO ...

    /* Dependencies provided from modules */
    // TODO ...

    /* Field injection */
    void inject(SplashActivity splashActivity);
    void inject(IntroActivity introActivity);
    void inject(HomeActivity activity);
    void inject(SignInActivity signInActivity);
    void inject(CommentActivity commentActivity);

}
