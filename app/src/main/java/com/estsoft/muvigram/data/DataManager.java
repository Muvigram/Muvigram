package com.estsoft.muvigram.data;


import android.provider.MediaStore;

import com.estsoft.muvigram.data.remote.FindFriendService;
import com.estsoft.muvigram.data.remote.LoginTestService;
import com.estsoft.muvigram.data.remote.MusicSelectService;
import com.estsoft.muvigram.data.remote.NetworkTestService;
import com.estsoft.muvigram.data.remote.ProfileThumbnailService;
import com.estsoft.muvigram.data.remote.ProfileUserInfoService;
import com.estsoft.muvigram.data.remote.SearchMusicService;
import com.estsoft.muvigram.data.remote.SearchTagService;
import com.estsoft.muvigram.data.remote.SearchUserService;
import com.estsoft.muvigram.data.remote.TrendingSoundsService;
import com.estsoft.muvigram.data.remote.TrendingTagsService;
import com.estsoft.muvigram.data.remote.TrendingUsersService;
import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.ProfileThumbnailRepo;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.model.TestRepo;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.search.SearchHeaderVideoItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Singleton
public class DataManager {

    private final NetworkTestService mNetworkTestService;
    private final LoginTestService mLoginTestService;
    private final MusicSelectService mMusicSelectService;
    private final FindFriendService mFindFriendService;

    //SearchFragment
    private final SearchUserService mSearchUserService;
    private final SearchTagService mSearchTagService;
    private final SearchMusicService mSearchMusicService;
    private final TrendingTagsService mTrendingTagsService;
    private final TrendingSoundsService mTrendingSoundsService;
    private final TrendingUsersService mTrendingUsersService;

    //ProfileFragment
    private final ProfileUserInfoService mProfileUserInfoService;
    private final ProfileThumbnailService mProfileThumbnailService;

    @Inject
    public DataManager(NetworkTestService networkTestService,
                       LoginTestService loginTestService,
                       MusicSelectService musicSelectService,
                       FindFriendService findFriendService,
                       SearchUserService searchUserService,
                       SearchTagService searchTagService,
                       SearchMusicService searchMusicService,
                       TrendingTagsService trendingTagsService,
                       TrendingSoundsService trendingSoundsService,
                       TrendingUsersService trendingUsersService,
                       ProfileUserInfoService userInfoService,
                       ProfileThumbnailService profileThumbnailService) {
        this.mNetworkTestService = networkTestService;
        this.mLoginTestService = loginTestService;
        this.mMusicSelectService = musicSelectService;
        this.mFindFriendService = findFriendService;
        this.mSearchUserService = searchUserService;
        this.mSearchTagService = searchTagService;
        this.mSearchMusicService = searchMusicService;
        this.mTrendingTagsService = trendingTagsService;
        this.mTrendingSoundsService = trendingSoundsService;
        this.mTrendingUsersService = trendingUsersService;
        this.mProfileUserInfoService = userInfoService;
        this.mProfileThumbnailService = profileThumbnailService;
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


    //SearchFragment
    public Observable<List<Friend>> getSearchUsers() {
        return mSearchUserService.getUsers();
    }
    public Observable<List<Tag>> getSearchTags(){
        return mSearchTagService.getTags();
    }
    public Observable<List<Music>> getSearchMusics() { return mSearchMusicService.getMusics();}
    public Observable<List<Tag>> getTrendingTags() { return  mTrendingTagsService.getTags(); }
    public Observable<List<Music>> getTrendingSounds() {return  mTrendingSoundsService.getMusics();}
    public Observable<List<Friend>> getTrendingUsers() {return  mTrendingUsersService.getFriends();}


    //ProfileFragment
    public Observable<List<Friend>> getFriends() {
        return mFindFriendService.getFriends();
    }
    public Observable<UserInfoRepo> getProfileUserInfo() { return  mProfileUserInfoService.getUserInfo();}
    public Observable<List<ProfileThumbnailRepo>> getProfileThumbnail() { return mProfileThumbnailService.getThumbnails();}


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
