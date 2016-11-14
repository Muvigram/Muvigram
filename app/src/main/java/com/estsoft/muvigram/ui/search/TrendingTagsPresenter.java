package com.estsoft.muvigram.ui.search;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

@PerParentFragment
public class TrendingTagsPresenter extends BasePresenter<TrendingTagsView>{

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    @Inject
    public TrendingTagsPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(TrendingTagsView trendingTagsView){
        super.attachView(trendingTagsView);
    }

    @Override
    public void detachView(){
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }



    public void loadTrendingTags(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getTrendingTags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        tags -> {
                            if (tags.isEmpty()) {
                                getMvpView().showTagsEmpty();
                            } else {
                                getMvpView().showTags(tags);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading tags");
                            getMvpView().showTagsError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }

    public void loadTrendingSounds(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getTrendingSounds()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        sounds -> {
                            if (sounds.isEmpty()) {
                                getMvpView().showSoundsEmpty();
                            } else {
                                getMvpView().showSounds(sounds);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading tags");
                            getMvpView().showSoundsError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }

    public void loadTrendingUsers(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getTrendingUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        users -> {
                            if (users.isEmpty()) {
                                getMvpView().showUsersEmpty();
                            } else {
                                getMvpView().showUsers(users);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading tags");
                            getMvpView().showUsersError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }
}
