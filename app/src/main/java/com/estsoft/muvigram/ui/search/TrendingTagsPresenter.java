package com.estsoft.muvigram.ui.search;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

public class TrendingTagsPresenter extends BasePresenter<TrendingTagsView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;

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
    }

    public void loadTags(){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);

        mSubscription = mDataManager.getTags()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        tags -> {
                            if (tags.isEmpty()){
                                getMvpView().showTagsEmpty();
                            }else {
                                getMvpView().showTags(tags);
                            }
                        },
                        e -> {
                            Timber.e(e,"There was an error loading tags");
                            getMvpView().showError();
                        }
                );
    }
}
