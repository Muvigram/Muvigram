package com.estsoft.muvigram.injection.module;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.MusicSelectService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.injection.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    /** Constructor */
    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    /* System */
    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


    /* Functionality */
    @Provides
    @Singleton
    NetworkTestService providesNetWorkTestService() {
        return new NetworkTestService();
    }

    @Provides
    @Singleton
    LoginTestService provideLoginService() {
        return LoginTestService.Creator.newLoginService();
    }

    @Provides
    @Singleton
    MusicSelectService provideMusicSelectService() {
        return MusicSelectService.Creator.newMusicSelectService();
    }

    //media scope!!
    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return mediaPlayer;
    }


}
