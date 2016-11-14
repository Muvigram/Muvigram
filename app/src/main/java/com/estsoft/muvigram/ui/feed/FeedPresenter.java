package com.estsoft.muvigram.ui.feed;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.model.FeedRepo;
import com.estsoft.muvigram.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by gangGongUi on 2016. 11. 8..
 */
@PerParentFragment
public class FeedPresenter extends BasePresenter<FeedView> {

    private final DataManager mDataManager;
    private final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Inject
    public FeedPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    public void recyclerViewItemActiveChanged(FeedItem feedItem){
        checkViewAttached();
        getMvpView().setMusicRecordViewImage(feedItem.getAlbum_cover());
    }

    public void loadFeedRepos() {
        checkViewAttached();
        getMvpView().showFeedLoadDialog(R.string.feed_dialog_text);
        final Subscription subscribe = mDataManager.getFeedRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(mFeedRepos -> {
                    List<FeedItem> feedItems = new ArrayList<>();
                    Observable.from(mFeedRepos)
                            .map(FeedRepo::toFeedItem)
                            .forEach(feedItem -> {
                                feedItem.setItemCallback(((FeedItem.ItemCallback) getMvpView()));
                                feedItems.add(feedItem);
                            });
                    return feedItems;
                })
                .subscribe(
                        getMvpView()::showFeedList,
                        e -> Timber.e(e.getMessage()),
                        getMvpView()::hideFeedLoadDialog
                );
        mCompositeSubscription.add(subscribe);
    }

    @Override public void detachView() {
        super.detachView();
        mCompositeSubscription.unsubscribe();
    }
}
