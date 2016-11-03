package com.estsoft.muvigram.ui.musicselectonline;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jaylim on 11/2/2016.
 */

@PerSingleFragment
public class MusicSelectOnlineListPresenter extends BasePresenter<MusicSelectOnlineListView> {

    final DataManager mDataManager;
    Subscription mSubscription;

    @Inject
    public MusicSelectOnlineListPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MusicSelectOnlineListView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription != null) mSubscription.unsubscribe();
    }

    /* Business Logic here ... */

    public void loadMusics() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMusics()
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
                            Timber.e(e, "There was an error loading the musics");
                            getMvpView().showError();
                        }
                );
    }
}
