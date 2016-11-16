package com.estsoft.muvigram.ui.notify;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.profile.ProfileFragmentView;

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
public class NotifyFragmentPresenter extends BasePresenter<NotifyFragmentView>{

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    @Inject
    public NotifyFragmentPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(NotifyFragmentView notifyFragmentView){
        super.attachView(notifyFragmentView);
    }

    @Override
    public void detachView(){
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }

    public void loadNotifyComments(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getNotifyComments()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        comments -> {
                            if (comments == null) {
                                getMvpView().showCommentsEmpty();
                            } else {
                                getMvpView().showComments(comments);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading userInfo");
                            e.printStackTrace();
                            getMvpView().showCommentsError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }

    public void loadNotifyFollow(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getNotifyFollow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        follows -> {
                            if (follows == null) {
                                getMvpView().showFollowEmpty();
                            } else {
                                getMvpView().showFollow(follows);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading userInfo");
                            e.printStackTrace();
                            getMvpView().showFollowError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }

    public void loadNotifyLikes(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getNotifyLikes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        likes -> {
                            if (likes == null) {
                                getMvpView().showLikesEmpty();
                            } else {
                                getMvpView().showLikes(likes);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading userInfo");
                            e.printStackTrace();
                            getMvpView().showLikesError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }


}
