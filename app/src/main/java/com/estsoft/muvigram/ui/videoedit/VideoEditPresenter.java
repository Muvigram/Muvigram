package com.estsoft.muvigram.ui.videoedit;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoEditPresenter extends BasePresenter<VideoEditView> {
    private static final String TAG = "VideoEditPresenter";

    final MediaManager mMediaManager;
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

}
