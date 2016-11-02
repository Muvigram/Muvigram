package com.estsoft.muvigram.ui.musicselect.online;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerNestedFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.base.Presenter;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jaylim on 11/1/2016.
 */


@PerNestedFragment
public class MusicSelectOnlinePresenter extends BasePresenter<MusicSelectView.OnlineView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MusicSelectOnlinePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MusicSelectView.OnlineView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription != null) mSubscription.unsubscribe();
    }

    /* Business Logic here ... */

    public void loadCategories() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        categories -> {
                            if (categories.isEmpty()) {
                                getMvpView().showCategoriesEmpty();
                            } else {
                                getMvpView().showCategories(categories);
                            }
                        },
                        e -> {
                            Timber.e(e, "There was an error loading the categories");
                            getMvpView().showError();
                        }

                );
    }

}
