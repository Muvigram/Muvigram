package com.estsoft.muvigram.injection.component;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.estsoft.muvigram.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * In android's perspective :
 *   1. It has the scope named {@link Singleton}
 *      whose life is bound to the lifecycle of the application.
 *   2. It is responsible for maintaining the objects
 *      that should be kept up along the whole application such as network connection.
 *
 * In dagger's perspective :
 *   1. The {@link ApplicationComponent} is the root component
 *      which lives as long as the {@link MuvigramApplication} does.
 *
 * In DI's perspective :
 *   1. Implicit dependency objects
 *     - {@link LoginTestService}
 *     - {@link NetworkTestService}
 *
 *   2. Explicit dependency objects
 *     - Context context()
 *     - Application application()
 *     - {@link DataManager}
 *
 * Created  by gangGongUi on 2016. 10. 9.
 * Modified by Jay Lim    on 2016. 10. 31.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    /* Subcomponent */

    /* Dependencies extended by constructor injections */
    DataManager dataManager();
    //media scope
    MediaManager mediaManager();
    MediaPlayer mediaPlayer();

    /* Dependencies provided from modules */
    @ApplicationContext Context context();
    Application application();

    /* Field injection */


}
