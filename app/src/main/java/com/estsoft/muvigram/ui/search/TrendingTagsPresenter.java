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

    public void loadVideo(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getSearchFragVideo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        video -> {
                            if (video == null) {
                                getMvpView().showVideoEmpty();
                            } else {
                                getMvpView().showVideo(video);
                            }
                        },
                        e -> {
                            Timber.e(e, "there was an error loading video");
                            e.printStackTrace();
                            getMvpView().showVideoError();
                        }
                );

        mCompositeSubscription.add(subscribe);
    }

    public void loadTags(){
        checkViewAttached();

        final Subscription subscribe = mDataManager.getTags()
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
                            getMvpView().showError();
                        }
                );
        mCompositeSubscription.add(subscribe);
    }
}
