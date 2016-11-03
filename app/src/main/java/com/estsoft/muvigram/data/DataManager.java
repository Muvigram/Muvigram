package com.estsoft.muvigram.data;


import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.MusicSelectService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.TestRepo;

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

    @Inject
    public DataManager(NetworkTestService mNetworkTestService,
                       LoginTestService mLoginTestService,
                       MusicSelectService mMusicSelectService) {
        this.mNetworkTestService = mNetworkTestService;
        this.mLoginTestService = mLoginTestService;
        this.mMusicSelectService = mMusicSelectService;

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
