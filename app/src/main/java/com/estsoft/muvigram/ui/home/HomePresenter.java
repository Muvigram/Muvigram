package com.estsoft.muvigram.ui.home;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public HomePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(HomeView mvpView) {
        super.attachView(mvpView);


        //Timber.e(mDataManager.getName());
    }

    public void loadTestData() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getNetworkTestService()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().showNetworkError();
                    }

                    @Override
                    public void onNext(String s) {
                        getMvpView().showTestToast(Integer.parseInt(s));
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
