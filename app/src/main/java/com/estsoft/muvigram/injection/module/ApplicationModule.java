package com.estsoft.muvigram.injection.module;

import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.estsoft.muvigram.data.remote.FindFriendService;
import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.MusicSelectService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.data.remote.SearchFragVideoService;
import com.estsoft.muvigram.data.remote.TrendingTagsService;
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


    /* Explicit Functionality */
    @Provides
    @Singleton
    MediaPlayer provideMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return mediaPlayer;
    }

    /* Implicit Functionality */
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

    @Provides
    @Singleton
    FindFriendService provideFindFriendService() {
        return FindFriendService.Creator.newFindFriendService();
    }

    @Provides
    @Singleton
    TrendingTagsService provideTrendingTagsService(){
        return TrendingTagsService.Creator.newTrendingTagsService();
    }

    @Provides
    @Singleton
    SearchFragVideoService provideSearchFragVideoService(){
        return SearchFragVideoService.Creator.newSearchFragVideoService();
    }


}
