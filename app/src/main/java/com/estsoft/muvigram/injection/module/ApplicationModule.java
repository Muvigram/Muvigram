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
import com.estsoft.muvigram.data.remote.SearchMusicService;
import com.estsoft.muvigram.data.remote.SearchTagService;
import com.estsoft.muvigram.data.remote.SearchUserService;
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
    NetworkTestService providesNetWorkTestService() {
        return new NetworkTestService();
    }

    @Provides
    LoginTestService provideLoginService() {
        return LoginTestService.Creator.newLoginService();
    }

    @Provides
    MusicSelectService provideMusicSelectService() {
        return MusicSelectService.Creator.newMusicSelectService();
    }

    @Provides
    FindFriendService provideFindFriendService() {
        return FindFriendService.Creator.newFindFriendService();
    }

    @Provides
    TrendingTagsService provideTrendingTagsService(){
        return TrendingTagsService.Creator.newTrendingTagsService();
    }

    @Provides
    SearchFragVideoService provideSearchFragVideoService(){
        return SearchFragVideoService.Creator.newSearchFragVideoService();
    }

    @Provides
    SearchUserService provideSearchUserService(){
        return SearchUserService.Creator.newSearchUserService();
    }

    @Provides
    SearchTagService provideSearchTagService(){
        return SearchTagService.Creator.newSearchTagService();
    }

    @Provides
    SearchMusicService provideSearchMusicService(){
        return SearchMusicService.Creator.newSearchMusicService();
    }
}
