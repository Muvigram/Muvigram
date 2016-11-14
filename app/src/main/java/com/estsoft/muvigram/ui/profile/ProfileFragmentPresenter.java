package com.estsoft.muvigram.ui.profile;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.search.TrendingTagsView;

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
public class ProfileFragmentPresenter extends BasePresenter<ProfileFragmentView>{

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    @Inject
    public ProfileFragmentPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ProfileFragmentView profileFragmentView){
        super.attachView(profileFragmentView);
    }

    @Override
    public void detachView(){
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }

    public void loadUserInfo(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getProfileUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        userInfo -> {
                            if (userInfo == null) {
                                getMvpView().showUserInfoEmpty();
                            } else {
                                getMvpView().showUserInfo(userInfo);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading userInfo");
                            e.printStackTrace();
                            getMvpView().showUserInfoError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }

    public void loadThumbnail(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getProfileThumbnail()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        thumbnail -> {
                            if (thumbnail == null) {
                                getMvpView().showThumbnailEmpty();
                            } else {
                                getMvpView().showThumbnail(thumbnail);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading thumbnails");
                            e.printStackTrace();
                            getMvpView().showThumbnailError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }


}
