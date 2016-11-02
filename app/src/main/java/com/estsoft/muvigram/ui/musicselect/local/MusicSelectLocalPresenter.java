package com.estsoft.muvigram.ui.musicselect.local;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.PerNestedFragment;
import com.estsoft.muvigram.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jaylim on 11/1/2016.
 */

@PerNestedFragment
public class MusicSelectLocalPresenter extends BasePresenter<MusicSelectView.LocalView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MusicSelectLocalPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MusicSelectView.LocalView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription != null) mSubscription.unsubscribe();
    }

    public void getMusics() {
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
                            Timber.e(e, "There was an error loading the ribots");
                            getMvpView().showError();
                        },
                        () -> {}
                );
    }
}
