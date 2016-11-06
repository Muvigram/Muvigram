package com.estsoft.muvigram.ui.friend;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.data.remote.FindFriendService;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.base.MvpView;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jaylim on 11/5/2016.
 */

@PerPlainActivity
public class FindFriendPresenter extends BasePresenter<FindFriendView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public FindFriendPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FindFriendView findFriendView) {
        super.attachView(findFriendView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadFriends() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);

        mSubscription = mDataManager.getFriends()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        friends -> {
                            if (friends.isEmpty()) {
                                getMvpView().showFriendsEmpty();
                            } else {
                                getMvpView().showFriends(friends);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading friends you may know.");
                            getMvpView().showError();
                        }
                );
    }
}
