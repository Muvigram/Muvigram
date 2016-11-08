package com.estsoft.muvigram.ui.search;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

@PerPlainActivity
public class SearchBarPresenter extends BasePresenter<SearchBarView>{

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    @Inject
    public SearchBarPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SearchBarView searchBarView){
        super.attachView(searchBarView);
    }

    @Override
    public void detachView(){
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }

    public void loadUsers(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getSearchUsers()
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
                            Timber.e(e, "There was an error loading users");
                            getMvpView().showUsersError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }

    public void loadTags(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getSearchTags()
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

    public void loadMusics(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getSearchMusics()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        musics -> {
                            if (musics.isEmpty()) {
                                getMvpView().showMusicsEmpty();
                            } else {
                                getMvpView().showMusics(musics);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading musics");
                            getMvpView().showMusicsError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }
}
