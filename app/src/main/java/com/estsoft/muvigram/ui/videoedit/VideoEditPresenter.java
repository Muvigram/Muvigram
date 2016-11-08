package com.estsoft.muvigram.ui.videoedit;

import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoEditPresenter extends BasePresenter<VideoEditView> {
    private static final String TAG = "VideoEditPresenter";

    final int NO_AUDIO = -1003;
    final int WITH_AUDIO = -1004;
    int mAudioStatus = NO_AUDIO;

    int mAudioOffsetMs;

    final MediaManager mMediaManager;
    String[] mAudioMetaDatas;
    String mTitle;
    Subscription mSubscription;


    @Inject
    public VideoEditPresenter( MediaManager mediaManager ) {
        mMediaManager = mediaManager;
    }

    @Override
    public void attachView(VideoEditView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    /* Business logic here ... */

    public void videoRestarted() {
        getMvpView().reStartVideoView( 0 );
        getMvpView().reStartAudio( mAudioOffsetMs );
    }

    public void setAudioOffset( int audioOffset ) {
        mAudioOffsetMs = audioOffset;
    }

    public void loadAudioMetaData( String path ) {
        mSubscription = mMediaManager.getAlbumMetaData(path).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        strings -> {
                            mAudioMetaDatas = strings;
                        },
                        e -> {
                            e.printStackTrace();
                        },
                        () -> {
                            RxUtil.unsubscribe(mSubscription);
                            mTitle = mAudioMetaDatas[0] + " - " + mAudioMetaDatas[1];
                            getMvpView().updateAudioTitle( mTitle );
                            if (mAudioMetaDatas[3].equals("") || mAudioMetaDatas[3] == null) {
                            } else {
                                getMvpView().updateAudioAlbum( mAudioMetaDatas[3] );
                            }
                        }
                        );

    }
}
