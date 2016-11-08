package com.estsoft.muvigram.ui.feed;

import android.view.View;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by gangGongUi on 2016. 11. 8..
 */
@PerParentFragment
public class FeedPresenter extends BasePresenter<FeedView>{

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Inject
    public FeedPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    public void recyclerViewItemActiveChanged(View newActiveView, int newActiveViewPosition){
        checkViewAttached();
        //getMvpView().setMusicRecordViewImage();
    }

    public void loadFeedRepos() {
        checkViewAttached();


    }


}
