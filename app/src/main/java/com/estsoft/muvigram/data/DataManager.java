package com.estsoft.muvigram.data;


import android.provider.MediaStore;

import com.estsoft.muvigram.data.remote.FindFriendService;
import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.MusicSelectService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.data.remote.SearchFragVideoService;
import com.estsoft.muvigram.data.remote.TrendingTagsService;
import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.model.TestRepo;
import com.estsoft.muvigram.ui.search.SearchHeaderVideoItem;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Singleton
public class DataManager {

    private final NetworkTestService mNetworkTestService;
    private final LoginTestService mLoginTestService;
    private final MusicSelectService mMusicSelectService;
    private final FindFriendService mFindFriendService;
    private final TrendingTagsService mTrendingTagsService;
    private final SearchFragVideoService mSearchFragVideoService;

    @Inject
    public DataManager(NetworkTestService networkTestService,
                       LoginTestService loginTestService,
                       MusicSelectService musicSelectService,
                       FindFriendService findFriendService,
                       TrendingTagsService trendingTagsService,
                       SearchFragVideoService searchFragVideoService) {
        this.mNetworkTestService = networkTestService;
        this.mLoginTestService = loginTestService;
        this.mMusicSelectService = musicSelectService;
        this.mFindFriendService = findFriendService;
        this.mTrendingTagsService = trendingTagsService;
        this.mSearchFragVideoService = searchFragVideoService;
    }

    public Observable<TestRepo> getLoginTestService() {
        return mLoginTestService.getLoginTest();
    }

    public Observable<String> getNetworkTestService() {
        return mNetworkTestService.getTestData();
    }

    public Observable<List<Category>> getCategories() {
        return mMusicSelectService.getCategries();
    }

    public Observable<List<Music>> getMusics() {
        return mMusicSelectService.getMusics();
    }

    public Observable<List<Friend>> getFriends() {
        return mFindFriendService.getFriends();
    }

    public Observable<List<Tag>> getTags() { return  mTrendingTagsService.getTags(); }

    public Observable<SearchHeaderVideoItem> getSearchFragVideo() { return mSearchFragVideoService.getVideo();}

//    public Observable<Category> getCategories() {
//        return mMusicSelectService.getCategries()
//                .concatMap(this::alignCategories);
//    }
//
//    public Observable<Music> getMusics() {
//        return mMusicSelectService.getMusics()
//                .concatMap(this::alignMusics);
//    }
//
//    private Observable<Category> alignCategories(Collection<Category> categories) {
//        return Observable.create(subscriber -> {
//            if (subscriber.isUnsubscribed()) {
//                return;
//            }
//            for (Category category : categories) {
//                // TODO - Do I have to check each category instance?
//                subscriber.onNext(category);
//            }
//            subscriber.onCompleted();
//        });
//    }
//
//    private Observable<Music> alignMusics(Collection<Music> musics) {
//        return Observable.create( subscriber -> {
//            if (subscriber.isUnsubscribed()) {
//                return;
//            }
//            for (Music music : musics) {
//                subscriber.onNext(music);
//            }
//            subscriber.onCompleted();
//        });
//    }
}
