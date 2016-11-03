package com.estsoft.muvigram.ui.videoselect;

import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.model.VideoMetaData;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoSelectPresenter extends BasePresenter<VideoSelectView> {
    private static final String TAG = "VideoSelectPresenter";

    final MediaManager mMediaManager;
    Subscription mSubscription;

    @Inject
    public VideoSelectPresenter(MediaManager mediaManager) {
        mMediaManager = mediaManager;
    }

    @Override
    public void attachView(VideoSelectView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here ... */
    public void loadThumbnails(){
        getMvpView().enableProgress();
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mMediaManager.getVideoMetaData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        videoMetaData -> {
                            getMvpView().updateThumbnails(videoMetaData);
                        },
                        throwable -> {
                            Timber.e(throwable, "There was an error loading the musics");
                            getMvpView().showError();
                        },
                        () -> {
                            getMvpView().disableProgress();
                        }
                );
    }


}
